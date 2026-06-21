package com.together.study.timer.manager

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.IBinder
import androidx.core.content.ContextCompat
import com.together.study.timer.service.TimerWorkingService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

private const val SCREEN_OFF_DETECTION_DELAY = 500L

@Singleton
class TimerManager @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private var isBound = false
    private val managerScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private var observeJob: Job? = null
    private var service: TimerWorkingService? = null
    private var pendingStartSubjectId: Long? = null

    private var accumulatedTime = 0
    private var isScreenOff = false

    private val _elapsedTime = MutableStateFlow(0)
    val elapsedTime = _elapsedTime.asStateFlow()
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    private val screenReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                Intent.ACTION_SCREEN_OFF -> isScreenOff = true
                Intent.ACTION_SCREEN_ON -> isScreenOff = false
            }
        }
    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            service = (binder as TimerWorkingService.BinderImpl).getService()
            observe()
            pendingStartSubjectId?.let { service?.start(it) }
            pendingStartSubjectId = null
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            service = null
            isBound = false
            observeJob?.cancel()
            observeJob = null
        }
    }

    fun bind() {
        if (isBound) return

        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_OFF)
            addAction(Intent.ACTION_SCREEN_ON)
        }
        ContextCompat.registerReceiver(
            context, screenReceiver, filter, ContextCompat.RECEIVER_NOT_EXPORTED
        )

        isBound = context.bindService(
            Intent(context, TimerWorkingService::class.java),
            connection,
            Context.BIND_AUTO_CREATE,
        )
    }

    fun unbind() {
        runCatching { context.unregisterReceiver(screenReceiver) }
        runCatching {
            if (isBound) {
                observeJob?.cancel()
                observeJob = null
                context.unbindService(connection)
                isBound = false
            }
        }
    }

    fun start(subjectId: Long) {
        val intent = Intent(context, TimerWorkingService::class.java).apply {
            putExtra("subjectId", subjectId)
        }
        ContextCompat.startForegroundService(context, intent)
        if (service == null) pendingStartSubjectId = subjectId
    }

    fun stop() {
        accumulatedTime = _elapsedTime.value
        service?.stop()
    }

    // 화면이 켜진 채로 나간 경우(홈 / 다른 앱 / 푸시)는 정지
    // 화면이 꺼진 경우(잠금 / 화면 타임아웃)는 유지
    fun onEnterBackground() {
        if (!_isPlaying.value) return
        managerScope.launch {
            delay(SCREEN_OFF_DETECTION_DELAY)
            if (!isScreenOff) stop()
        }
    }

    fun resetAccumulatedTime() {
        accumulatedTime = 0
        _elapsedTime.value = 0
    }

    private fun observe() {
        service?.let { svc ->
            observeJob?.cancel()
            observeJob = managerScope.launch {
                launch { svc.elapsedTime.collect { _elapsedTime.value = accumulatedTime + it } }
                launch { svc.isPlaying.collect { _isPlaying.value = it } }
            }
        }
    }
}
