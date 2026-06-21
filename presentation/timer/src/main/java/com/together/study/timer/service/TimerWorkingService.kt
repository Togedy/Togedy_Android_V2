package com.together.study.timer.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TimerWorkingService : Service() {

    @Inject
    lateinit var startTimerUseCase: StartTimerUseCase
    @Inject
    lateinit var stopTimerUseCase: StopTimerUseCase

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val _elapsedTime = MutableStateFlow(0)
    val elapsedTime: StateFlow<Int> = _elapsedTime

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private var timerJob: Job? = null
    private var timerId: Long? = null

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
                .onSuccess {
                    timerId = it.timerId
                    _isPlaying.value = true
                    startTicking()
                }
                .onFailure {
                    stopForeground(STOP_FOREGROUND_REMOVE)
                    stopSelf()
                }
        }
    }

    fun stop() {
        val id = timerId ?: return

        scope.launch {
            try {
                stopTimerUseCase(id)
            } catch (e: Exception) {
                // fallback (WorkManager)
            }
        }

        stopTicking()
        _isPlaying.value = false
        timerId = null

        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun startTicking() {
        timerJob?.cancel()
        _elapsedTime.value = 0
        timerJob = scope.launch {
            while (true) {
                delay(1000)
                _elapsedTime.update { it + 1 }
            }
        }
    }

    private fun stopTicking() {
        timerJob?.cancel()
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
        scope.cancel()
    }
}