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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimerManager @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private var isBound = false

    private var service: TimerWorkingService? = null
    private var pendingStartSubjectId: Long? = null

    val elapsedTime = MutableStateFlow(0)
    val isPlaying = MutableStateFlow(false)

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
        }
    }

    fun bind() {
        if (isBound) return

        val result = context.bindService(
            Intent(context, TimerWorkingService::class.java),
            connection,
            Context.BIND_AUTO_CREATE
        )

        isBound = result
    }

    fun unbind() {
        runCatching {
            if (isBound) {
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
            pendingStartSubjectId = subjectId  // 🔥 저장
        }
    }

    fun stop() {
        service?.stop()
    }

    private fun observe() {
        service?.let { svc ->
            CoroutineScope(Dispatchers.Main).launch {
                svc.elapsedTime.collect { elapsedTime.value = it }
            }
            CoroutineScope(Dispatchers.Main).launch {
                svc.isPlaying.collect { isPlaying.value = it }
            }
        }
    }
}
