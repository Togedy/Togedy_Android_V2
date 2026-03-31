package com.together.study.planner.model

data class ShareInfo(
    val date: String,
    val hasDday: Boolean,
    val userScheduleName: String?,
    val remainingDays: Int?,
    val totalStudyTime: String,
    val image: String?,
    val plannerItems: List<PlannerSubject>,
    val timeTables: List<TimeTable>,
)
