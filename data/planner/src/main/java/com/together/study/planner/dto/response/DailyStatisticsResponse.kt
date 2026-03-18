package com.together.study.planner.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class DailyStatisticsResponse(
    val daysSinceLastStudy: Int,
    val currentStreakDays: Int,
    val weeklyReview: List<String?>,
    val monthlyReview: List<Int>,
)
