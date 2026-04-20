package com.together.study.timer.dto

import kotlinx.serialization.Serializable

@Serializable
data class StopTimerRequest(
    val timerId: Long,
)