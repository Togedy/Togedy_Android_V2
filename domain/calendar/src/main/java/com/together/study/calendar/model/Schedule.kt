package com.together.study.calendar.model

data class Schedule(
    val scheduleId: Long? = null,
    val scheduleType: String,
    val scheduleName: String,
    val startDate: String,
    val startTime: String? = null,
    val endTime: String? = null,
    val endDate: String? = null,
    val universityAdmissionType: String = "",
    val universityAdmissionStage: String = "",
    val universityAdmissionMethod: String = "",
    val category: Category,
)
