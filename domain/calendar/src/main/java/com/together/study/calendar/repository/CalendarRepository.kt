package com.together.study.calendar.repository

import com.together.study.calendar.model.Announcement
import com.together.study.calendar.model.DDay
import com.together.study.calendar.model.DailyScheduleInfo
import com.together.study.calendar.model.Schedule

interface CalendarRepository {
    suspend fun getMonthlySchedule(month: String): Result<List<Schedule>>
    suspend fun getDailySchedule(date: String): Result<DailyScheduleInfo>
    suspend fun getDDay(): Result<DDay>
    suspend fun getAnnouncement(): Result<Announcement>
}
