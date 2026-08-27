package com.together.study.timer.service

import com.together.study.remote.model.BaseResponse
import com.together.study.timer.dto.RunningTimerResponse
import com.together.study.timer.dto.StartTimerRequest
import com.together.study.timer.dto.StopTimerRequest
import com.together.study.timer.dto.SubjectTimerResponse
import com.together.study.timer.dto.TimerResponse
import com.together.study.timer.dto.TotalTimerResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface TimerService {
    @GET("timers/total")
    suspend fun getTotalStudyTimer(): BaseResponse<TotalTimerResponse>

    @GET("timers/running")
    suspend fun getRunningTimer(): BaseResponse<RunningTimerResponse>

    @GET("timers/summary")
    suspend fun getSummaryTimer(): BaseResponse<List<SubjectTimerResponse>>

    @POST("timers/stop")
    suspend fun stopTimer(
        @Body timerId: StopTimerRequest,
    ): BaseResponse<TimerResponse>

    @POST("timers/start")
    suspend fun startTimer(
        @Body subjectId: StartTimerRequest,
    ): BaseResponse<TimerResponse>

    @PATCH("timers/{timerId}/heartbeat")
    suspend fun sendHeartbeat(
        @Path("timerId") timerId: Long,
    ): Response<Unit>
}