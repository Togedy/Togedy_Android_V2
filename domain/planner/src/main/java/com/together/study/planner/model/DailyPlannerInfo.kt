package com.together.study.planner.model

data class DailyPlannerInfo(
    val date: String,
    val hasDday: Boolean,
    val userScheduleName: String?,
    val remainingDays: Int?,
    val totalStudyTime: String,
    val plannerImage: String?,
)
