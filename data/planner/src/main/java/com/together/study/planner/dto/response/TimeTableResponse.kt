package com.together.study.planner.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class TimeTableResponse(
    val startTime: String,
    val endTime: String,
    val subjectColor: String,
)
