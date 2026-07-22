package com.together.study.planner.service

import com.together.study.planner.dto.request.PlannerTaskRequest
import com.together.study.planner.dto.request.PlannerTaskStateRequest
import com.together.study.planner.dto.response.DailyPlannerTaskListResponse
import com.together.study.remote.model.BaseResponse
import com.together.study.remote.model.EmptyDataResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PlannerTaskService {
    @GET("planners/daily/tasks")
    suspend fun getPlannerTaskList(
        @Query("date") date: String,
    ): BaseResponse<DailyPlannerTaskListResponse>

    @PUT("planners/daily/tasks")
    suspend fun updateTaskContent(
        @Body request: PlannerTaskRequest,
    ): BaseResponse<Long>

    @DELETE("planners/daily/tasks/{taskId}")
    suspend fun deleteTask(
        @Path("taskId") taskId: Long,
    ): EmptyDataResponse

    @PATCH("planners/daily/tasks/{taskId}/check")
    suspend fun updateTaskChecked(
        @Path("taskId") taskId: Long,
        @Body request: PlannerTaskStateRequest,
    ): EmptyDataResponse
}
