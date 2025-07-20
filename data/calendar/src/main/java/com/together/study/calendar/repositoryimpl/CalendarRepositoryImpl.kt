package com.together.study.calendar.repositoryimpl

import com.together.study.calendar.datasouce.CalendarDataSource
import com.together.study.calendar.mapper.toDomain
import com.together.study.calendar.model.Announcement
import com.together.study.calendar.model.DDay
import com.together.study.calendar.model.Schedule
import com.together.study.calendar.repository.CalendarRepository
import javax.inject.Inject

class CalendarRepositoryImpl @Inject constructor(
    private val calendarDataSource: CalendarDataSource,
) : CalendarRepository {
    override suspend fun getMonthlySchedule(month: String): Result<List<Schedule>> =
        runCatching {
            val response = calendarDataSource.getMonthlySchedule(month = month).response
            response.toDomain()
        }

    override suspend fun getDailySchedule(date: String): Result<List<Schedule>> =
        runCatching {
            val response = calendarDataSource.getDailySchedule(date = date).response
            response.toDomain()
        }


    override suspend fun getDDay(): Result<DDay> =
        runCatching {
            val response = calendarDataSource.getDDay().response
            response.toDomain()
        }

    override suspend fun getAnnouncement(): Result<Announcement> =
        runCatching {
            val response = calendarDataSource.getAnnouncement().response
            response.toDomain()
        }

}
