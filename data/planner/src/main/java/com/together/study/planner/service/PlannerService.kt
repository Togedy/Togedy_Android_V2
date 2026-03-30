package com.together.study.planner.service

import com.together.study.planner.dto.response.DailyStatisticsResponse
import com.together.study.planner.dto.response.DailyTimeTableResponse
import com.together.study.planner.dto.response.DailyPlannerInfoResponse
import com.together.study.planner.dto.response.MonthlyHeatmapResponse
import com.together.study.remote.model.BaseResponse
import com.together.study.remote.model.EmptyDataResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PlannerService {
    @POST("planners/daily/image") //TODO: Image 연결
    suspend fun postDailyPlannerImage(
        @Query("date") date: String,

    ): EmptyDataResponse

    @GET("planners/daily")
    suspend fun getDailyPlannerInfo(
        @Query("date") date: String,
    ): BaseResponse<DailyPlannerInfoResponse>

    @GET("planners/daily/statistics")
    suspend fun getDailyStatistics(
        @Query("date") date: String,
    ): BaseResponse<DailyStatisticsResponse>

    @GET("planners/daily/timetables")
    suspend fun getDailyTimetable(
        @Query("date") date: String,
    ): BaseResponse<DailyTimeTableResponse>

    @GET("planners/monthly")
    suspend fun getMonthlyHeatmap(
        @Query("month") month: String,
    ): BaseResponse<MonthlyHeatmapResponse>
}
