package com.together.study.study.service

import com.together.study.remote.model.BaseResponse
import com.together.study.remote.model.EmptyDataResponse
import com.together.study.study.dto.StudyMemberPlannerResponse
import com.together.study.study.dto.StudyMemberProfileResponse
import com.together.study.study.dto.StudyMemberTimeBlockResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface StudyMemberService {
    @GET("studies/{studyId}/members/{userId}/profiles")
    suspend fun getStudyMemberProfile(
        @Path("studyId") studyId: Long,
        @Path("userId") userId: Long,
    ): BaseResponse<StudyMemberProfileResponse>

    @GET("studies/{studyId}/members/{userId}/study-times")
    suspend fun getStudyMemberTimeBlocks(
        @Path("studyId") studyId: Long,
        @Path("userId") userId: Long,
    ): BaseResponse<StudyMemberTimeBlockResponse>

    @GET("studies/{studyId}/members/{userId}/planners")
    suspend fun getStudyMemberPlanner(
        @Path("studyId") studyId: Long,
        @Path("userId") userId: Long,
    ): BaseResponse<StudyMemberPlannerResponse>

    @POST("studies/{studyId}/members/{userId}/planners/visibility")
    suspend fun postPlannerVisibility(
        @Path("studyId") studyId: Long,
        @Path("userId") userId: Long,
        @Body isPlannerVisible: Boolean,
    ): EmptyDataResponse
}
