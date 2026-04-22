package com.together.study.calendar.model

data class DailyScheduleInfo(
    val remainingDays: Int? = null,
    val dailyScheduleList: List<Schedule>,
)
