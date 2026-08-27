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

private const val TAG = "TimerService"
private const val HEARTBEAT_INTERVAL = 60_000L
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

    private var timerJob: Job? = null
    private var heartbeatJob: Job? = null
    private var timerId: Long? = null
    private var wakeLock: PowerManager.WakeLock? = null
    private var startRealtime = 0L // 도즈모드 경과시간
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

    private fun startHeartbeat(id: Long) {
        heartbeatJob?.cancel()
        heartbeatJob = scope.launch {
            sendHeartbeat(id)
            while (isActive) {
                delay(HEARTBEAT_INTERVAL)
                sendHeartbeat(id)
            }
        }
    }

    private suspend fun sendHeartbeat(id: Long) {
        sendTimerHeartbeatUseCase(id)
            .onSuccess {
                Timber.tag(TAG).d("heartbeat 성공, timerId=$id")
            }
            .onFailure { e ->
                handleHeartbeatFailure(id, e)
            }
    }

    /**
     * 200 아닌 모든 응답과 네트워크 오류를 타이머 측정 중단으로 판단
     * 추후 네트워크 오류 및 서버 내부 장애에 대한 판단 필요
     */
    private fun handleHeartbeatFailure(id: Long, e: Throwable) {
        Timber.tag(TAG).w(e, "heartbeat 실패, timerId=$id")
        _isConnectionLost.value = true
        stop()
    }

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