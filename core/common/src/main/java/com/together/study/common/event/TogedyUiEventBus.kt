package com.together.study.common.event

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object TogedyUiEventBus {
    private val _event = MutableSharedFlow<TogedyUiEvent>(
        extraBufferCapacity = 1
    )
    val event = _event.asSharedFlow()

    fun send(event: TogedyUiEvent) {
        _event.tryEmit(event)
    }
}