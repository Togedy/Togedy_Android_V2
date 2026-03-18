package com.together.study.planner.dto.response

data class DailyStatisticsResponse(
    val daysSinceLastStudy: Int,
    val currentStreakDays: Int,
    val weeklyReview: List<String?>,
    val monthlyReview: List<Int>,
)
