package com.together.study.planner.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class DailyTimeTableResponse(
    val timeTableList: List<TimeTableResponse>,
)
