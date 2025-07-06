package com.together.study.calendar.service

import com.together.study.calendar.dto.DDayResponse
import com.together.study.calendar.dto.ScheduleResponse
import com.together.study.remote.model.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CalendarService {
    @GET("calendars/monthly?")
    suspend fun getMonthlySchedule(
        @Path("month") month: String,
    ): BaseResponse<List<ScheduleResponse>>

    @GET("calendars/daily")
    suspend fun getDailySchedule(
        @Path("date") date: String,
    ): BaseResponse<List<ScheduleResponse>>

    @GET("calendars/d-day")
    suspend fun getDDay(): BaseResponse<DDayResponse>

    @GET("calendars/announcement")
    suspend fun getAnnouncement(): BaseResponse<Pair<Boolean, String>>
}
