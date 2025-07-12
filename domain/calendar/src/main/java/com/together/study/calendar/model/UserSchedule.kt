package com.together.study.calendar.model

data class UserSchedule(
    val userScheduleName: String,
    val startDate: String,
    val startTime: String? = null,
    val endDate: String? = null,
    val endTime: String? = null,
    val categoryId: Long,
    val memo: String? = null,
    val dDay: Boolean,
)
