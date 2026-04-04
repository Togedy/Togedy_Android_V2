package com.together.study.planner.service

import com.together.study.planner.dto.request.SubjectIndexRequest
import com.together.study.planner.dto.request.SubjectItemRequest
import com.together.study.planner.dto.response.SubjectItemResponse
import com.together.study.remote.model.BaseResponse
import com.together.study.remote.model.EmptyDataResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface PlannerSubjectService {
    @GET("planners/subjects")
    suspend fun getSubjects(): BaseResponse<List<SubjectItemResponse>>

    @POST("planners/subjects")
    suspend fun postSubject(
        @Body request: SubjectItemRequest,
    ): EmptyDataResponse

    @PATCH("planners/subjects/{subjectId}")
    suspend fun patchSubject(
        @Body request: SubjectItemRequest,
        @Path("subjectId") subjectId: Long,
    ): EmptyDataResponse

    @DELETE("planners/subjects/{subjectId}")
    suspend fun deleteSubject(
        @Path("subjectId") subjectId: Long,
    ): EmptyDataResponse

    @PATCH("planners/subjects/{subjectId}/move")
    suspend fun moveSubjectIndex(
        @Body request: SubjectIndexRequest,
        @Path("subjectId") subjectId: Long,
    ): EmptyDataResponse
}
