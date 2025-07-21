package com.together.study.calendar.service

import com.together.study.calendar.dto.AnnouncementResponse
import com.together.study.calendar.dto.DDayResponse
import com.together.study.calendar.dto.DailyScheduleListResponse
import com.together.study.calendar.dto.MonthlyScheduleListResponse
import com.together.study.remote.model.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CalendarService {
    @GET("calendars/monthly")
    suspend fun getMonthlySchedule(
        @Query("month") month: String,
    ): BaseResponse<MonthlyScheduleListResponse>

    @GET("calendars/daily")
    suspend fun getDailySchedule(
        @Query("date") date: String,
    ): BaseResponse<DailyScheduleListResponse>

    @GET("calendars/d-day")
    suspend fun getDDay(): BaseResponse<DDayResponse>

    @GET("calendars/announcement")
    suspend fun getAnnouncement(): BaseResponse<AnnouncementResponse>
}
