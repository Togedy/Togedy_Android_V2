package com.together.study.timer.dto

import kotlinx.serialization.Serializable

@Serializable
data class TotalTimerResponse(
    val studyTime: Long,
)
