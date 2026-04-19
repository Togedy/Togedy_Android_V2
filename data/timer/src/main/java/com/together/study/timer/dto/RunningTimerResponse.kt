package com.together.study.timer.dto

import kotlinx.serialization.Serializable

@Serializable
data class RunningTimerResponse(
    val timerId: Long,
    val subjectId: Long,
    val startTime: String,
)
