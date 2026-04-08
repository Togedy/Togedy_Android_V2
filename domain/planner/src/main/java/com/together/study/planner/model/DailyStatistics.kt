package com.together.study.planner.model

data class DailyStatistics(
    val daysSinceLastStudy: Int,
    val currentStreakDays: Int,
    val weeklyReview: List<String?>,
    val monthlyReview: List<Int>,
)
