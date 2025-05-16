package com.together.study.calendar.model

data class Schedule(
    val scheduleId: Long? = null,
    val scheduleType: String,
    val scheduleName: String,
    val startDate: String,
    val endDate: String? = null,
    val universityAdmissionType: String = "",
    val universityAdmissionStage: String = "",
    val category: Category? = null,
)
