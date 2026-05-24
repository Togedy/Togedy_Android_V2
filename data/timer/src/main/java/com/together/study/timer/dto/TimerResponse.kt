package com.together.study.timer.dto

import kotlinx.serialization.Serializable

@Serializable
data class TimerResponse(
    val timerId: Long,
    val startTime: String,
    val endTime: String? = null,
)
