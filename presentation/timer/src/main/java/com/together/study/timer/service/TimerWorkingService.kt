package com.together.study.timer.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.PowerManager
import android.os.SystemClock
import androidx.core.app.NotificationCompat
import com.together.study.timer.model.TimerHeartbeatException
import com.together.study.timer.usecase.SendTimerHeartbeatUseCase
import com.together.study.timer.usecase.StartTimerUseCase
import com.together.study.timer.usecase.StopTimerUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import timber.log.Timber
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

private const val TAG = "TimerService"
private const val HEARTBEAT_INTERVAL = 60_000L
private const val HEARTBEAT_RETRY_INTERVAL = 20_000L
private const val HEARTBEAT_GRACE_PERIOD = 150_000L
private const val WAKE_LOCK_TAG = "Togedy:timer"
private const val WAKE_LOCK_TIMEOUT = 12 * 60 * 60 * 1000L
private const val STOP_TIMEOUT = 5_000L

@AndroidEntryPoint
class TimerWorkingService : Service() {

    @Inject
    lateinit var startTimerUseCase: StartTimerUseCase
    @Inject
    lateinit var stopTimerUseCase: StopTimerUseCase
    @Inject
    lateinit var sendTimerHeartbeatUseCase: SendTimerHeartbeatUseCase

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val _elapsedTime = MutableStateFlow(0)
    val elapsedTime: StateFlow<Int> = _elapsedTime

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private val _isConnectionLost = MutableStateFlow(false) // 서버 종료된 경우
    val isConnectionLost: StateFlow<Boolean> = _isConnectionLost

    private val _isHeartbeatUnstable = MutableStateFlow(false) // 유예 시간 안에서 재시도 중인 경우
    val isHeartbeatUnstable: StateFlow<Boolean> = _isHeartbeatUnstable

    private var timerJob: Job? = null
    private var heartbeatJob: Job? = null
    private var timerId: Long? = null
    private var wakeLock: PowerManager.WakeLock? = null
    private var startRealtime = 0L // 도즈모드 경과시간
    private var lastHeartbeatRealtime = 0L // 마지막 하트비트 성공 시점
    private var heartbeatFailureCount = 0
    private var isStopping = false

    inner class BinderImpl : Binder() {
        fun getService() = this@TimerWorkingService
    }

    override fun onBind(intent: Intent?): IBinder = BinderImpl()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForegroundInternal()

        val subjectId = intent?.getLongExtra("subjectId", -1L) ?: -1L
        if (subjectId != -1L && !_isPlaying.value) {
            scope.launch { start(subjectId) }
        }

        return START_NOT_STICKY
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        stop()
    }

    fun start(subjectId: Long) {
        if (_isPlaying.value) return

        scope.launch {
            startTimerUseCase(subjectId)
                .onSuccess { timer ->
                    timerId = timer.timerId
                    _isConnectionLost.value = false
                    _isPlaying.value = true
                    acquireWakeLock()
                    startTicking()
                    startHeartbeat(timer.timerId)
                }
                .onFailure {
                    stopForeground(STOP_FOREGROUND_REMOVE)
                    stopSelf()
                }
        }
    }

    /**
     * 로컬 상태는 즉시 정리하고, 서버 종료 요청 후 서비스 종료
     */
    fun stop() {
        val id = timerId ?: return
        if (isStopping) return
        isStopping = true

        heartbeatJob?.cancel()
        heartbeatJob = null
        heartbeatFailureCount = 0
        _isHeartbeatUnstable.value = false

        stopTicking()
        _isPlaying.value = false
        timerId = null

        scope.launch {
            withTimeoutOrNull(STOP_TIMEOUT) { runCatching { stopTimerUseCase(id) } }
            withContext(Dispatchers.Main) {
                isStopping = false
                if (_isPlaying.value) return@withContext

                releaseWakeLock()
                stopForeground(STOP_FOREGROUND_REMOVE)
                stopSelf()
            }
        }
    }

    /**
     * 앱 포그라운드 복귀 시 서버 상태와 싱크
     */
    fun syncNow() {
        val id = timerId ?: return
        if (!_isPlaying.value) return
        scope.launch { sendHeartbeat(id) }
    }

    fun clearConnectionLost() {
        _isConnectionLost.value = false
    }

    private fun startTicking() {
        timerJob?.cancel()
        startRealtime = SystemClock.elapsedRealtime()
        _elapsedTime.value = 0
        timerJob = scope.launch {
            while (isActive) {
                val elapsed = SystemClock.elapsedRealtime() - startRealtime
                _elapsedTime.value = (elapsed / 1000).toInt()
                delay(1000 - (elapsed % 1000))
            }
        }
    }

    private fun stopTicking() {
        timerJob?.cancel()
        timerJob = null
    }

    /**
     * 하트비트 정책
     * - 성공: 60초 뒤 다음 하트비트
     * - 일시 실패(와이파이 끊김 / 서버 일시 장애): 20초 간격으로 재시도하고 타이머는 계속 진행
     * - 마지막 성공으로부터 150초(CUTOFF_SECONDS) 지연 시 로컬 정리
     */
    private fun startHeartbeat(id: Long) {
        heartbeatJob?.cancel()
        lastHeartbeatRealtime = SystemClock.elapsedRealtime()
        heartbeatFailureCount = 0
        _isHeartbeatUnstable.value = false

        heartbeatJob = scope.launch {
            while (isActive) {
                sendHeartbeat(id)
                delay(
                    (if (heartbeatFailureCount == 0) HEARTBEAT_INTERVAL
                    else HEARTBEAT_RETRY_INTERVAL).milliseconds
                )
            }
        }
    }

    private suspend fun sendHeartbeat(id: Long) {
        sendTimerHeartbeatUseCase(id)
            .onSuccess {
                lastHeartbeatRealtime = SystemClock.elapsedRealtime()
                heartbeatFailureCount = 0
                _isHeartbeatUnstable.value = false
            }
            .onFailure { e ->
                handleHeartbeatFailure(id, e)
            }
    }

    /**
     * 403/404/409 는 서버 타이머 정리로 즉시 종료 / 그 외 재시도
     */
    private fun handleHeartbeatFailure(id: Long, e: Throwable) {
        if (e is TimerHeartbeatException.Invalidated) {
            stopByServer()
            return
        }

        heartbeatFailureCount++
        _isHeartbeatUnstable.value = true

        val sinceLastSuccess = SystemClock.elapsedRealtime() - lastHeartbeatRealtime

        if (sinceLastSuccess >= HEARTBEAT_GRACE_PERIOD) {
            stopByServer()
        }
    }

    /**
     * 이미 서버에서 종료된 타이머를 로컬 정리
     */
    private fun stopByServer() {
        if (timerId == null) return

        timerId = null
        heartbeatJob?.cancel()
        heartbeatJob = null
        heartbeatFailureCount = 0
        stopTicking()

        _elapsedTime.value = elapsedTimeAtLastHeartbeat()
        _isPlaying.value = false
        _isHeartbeatUnstable.value = false
        _isConnectionLost.value = true

        scope.launch(Dispatchers.Main) {
            releaseWakeLock()
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
        }
    }

    private fun elapsedTimeAtLastHeartbeat(): Int =
        ((lastHeartbeatRealtime - startRealtime).coerceAtLeast(0L) / 1000).toInt()

    private fun acquireWakeLock() {
        if (wakeLock?.isHeld == true) return

        wakeLock = getSystemService(PowerManager::class.java)
            .newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, WAKE_LOCK_TAG)
            .apply {
                setReferenceCounted(false)
                acquire(WAKE_LOCK_TIMEOUT)
            }
    }

    private fun releaseWakeLock() {
        wakeLock?.let { if (it.isHeld) it.release() }
        wakeLock = null
    }

    private fun startForegroundInternal() {
        createNotificationChannel()

        val notification = NotificationCompat.Builder(this, "timer_channel")
            .setContentTitle("타이머 실행 중")
            .setContentText("공부 시간 측정 중")
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setOngoing(true)
            .build()

        startForeground(1, notification)
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            "timer_channel",
            "Timer Service",
            NotificationManager.IMPORTANCE_LOW
        )

        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseWakeLock()
        scope.cancel()
    }
}