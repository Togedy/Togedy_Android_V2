package com.together.study.timer.manager

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.core.content.ContextCompat
import com.together.study.timer.service.TimerWorkingService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimerManager @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private var isBound = false
    private val managerScope = CoroutineScope(
        SupervisorJob() + Dispatchers.Main.immediate
    )

    private var observeJob: Job? = null

    private var service: TimerWorkingService? = null
    private var pendingStartSubjectId: Long? = null

    private val _elapsedTime = MutableStateFlow(0)
    val elapsedTime = _elapsedTime.asStateFlow()
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            service = (binder as TimerWorkingService.BinderImpl).getService()

            observe()

            pendingStartSubjectId?.let {
                service?.start(it)
                pendingStartSubjectId = null
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            service = null
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
        val intent = Intent(context, TimerWorkingService::class.java)
        ContextCompat.startForegroundService(context, intent)

        if (service != null) {
            service?.start(subjectId)
        } else {
            pendingStartSubjectId = subjectId
        }
    }

    fun stop() {
        service?.stop()
    }

    private fun observe() {
        service?.let { svc ->
            observeJob?.cancel()
            
            observeJob = managerScope.launch {
                launch {
                    svc.elapsedTime.collect {
                        _elapsedTime.value = it
                    }
                }

                launch {
                    svc.isPlaying.collect {
                        _isPlaying.value = it
                    }
                }
            }
        }
    }
}
