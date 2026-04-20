package com.together.study.timer.dto

import kotlinx.serialization.Serializable

@Serializable
data class StartTimerRequest(
    val subjectId: Long,
)
