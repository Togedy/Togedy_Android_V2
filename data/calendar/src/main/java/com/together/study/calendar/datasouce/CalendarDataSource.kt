package com.together.study.calendar.datasouce

import com.together.study.calendar.service.CalendarService
import javax.inject.Inject

class CalendarDataSource @Inject constructor(
    private val calendarService: CalendarService,
) {
    suspend fun getMonthlySchedule(month: String) =
        calendarService.getMonthlySchedule(month = month)

    suspend fun getDailySchedule(date: String) = calendarService.getDailySchedule(date = date)

    suspend fun getDDay() = calendarService.getDDay()

    suspend fun getAnnouncement() = calendarService.getAnnouncement()
}
