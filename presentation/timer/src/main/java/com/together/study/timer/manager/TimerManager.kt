package com.together.study.timer.manager

import android.app.KeyguardManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.PowerManager
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
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "TimerManager"
private const val SCREEN_STATE_SETTLE_DELAY = 500L

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

    private val powerManager by lazy { context.getSystemService(PowerManager::class.java) }
    private val keyguardManager by lazy { context.getSystemService(KeyguardManager::class.java) }

    private val _elapsedTime = MutableStateFlow(0)
    val elapsedTime = _elapsedTime.asStateFlow()
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()
    private val _isConnectionLost = MutableStateFlow(false)
    val isConnectionLost = _isConnectionLost.asStateFlow()

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

        isBound = context.bindService(
            Intent(context, TimerWorkingService::class.java),
            connection,
            Context.BIND_AUTO_CREATE,
        )
    }

    fun unbind() {
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

    /**
     * 화면이 켜져 있고 잠금도 걸려 있지 않은 채로 앱을 벗어난 경우(홈 / 다른 앱 / 최근앱) 정지
     */
    fun onEnterBackground() {
        if (!_isPlaying.value) return
        managerScope.launch {
            delay(SCREEN_STATE_SETTLE_DELAY)

            val isScreenOn = powerManager.isInteractive
            val isLocked = keyguardManager.isKeyguardLocked

            if (isScreenOn && !isLocked) {
                Timber.tag(TAG).d("화면 켜진 상태로 앱 이탈")
                stop()
            } else {
                Timber.tag(TAG).d("잠금/화면 꺼짐")
            }
        }
    }

    /**
     * 포그라운드 복귀 시 서버가 타이머를 이미 종료했는지 확인
     */
    fun onEnterForeground() {
        service?.syncNow()
    }

    fun clearConnectionLost() {
        _isConnectionLost.value = false
        service?.clearConnectionLost()
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
                launch { svc.isConnectionLost.collect { _isConnectionLost.value = it } }
            }
        }
    }
}
