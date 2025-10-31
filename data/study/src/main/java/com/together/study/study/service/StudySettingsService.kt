package com.together.study.study.service

import com.together.study.remote.model.BaseResponse
import com.together.study.remote.model.EmptyDataResponse
import com.together.study.study.dto.StudyMemberBriefInfoResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface StudySettingsService {
    @GET("studies/{studyId}/members/management")
    suspend fun getStudyMembers(
        @Path("studyId") studyId: Long,
    ): BaseResponse<List<StudyMemberBriefInfoResponse>>

    @PATCH("sutdies/{studyId}/members/limit")
    suspend fun updateStudyMemberLimit(
        @Path("studyId") studyId: Long,
        @Body studyMemberLimit: Int,
    ): EmptyDataResponse

    @DELETE("studies/{studyId}")
    suspend fun deleteStudyAsLeader(
        @Path("studyId") studyId: Long,
    ): EmptyDataResponse

    @DELETE("studies/{studyId}/members/me")
    suspend fun deleteStudyAsMember(
        @Path("studyId") studyId: Long,
    ): EmptyDataResponse

    @DELETE("studies/{studyId}/members/{userId}")
    suspend fun deleteMemberFromStudy(
        @Path("studyId") studyId: Long,
        @Path("userId") userId: Long,
    ): EmptyDataResponse

    @PATCH("studies/{studyId}/members/{userId}/leader")
    suspend fun updateStudyLeader(
        @Path("studyId") studyId: Long,
        @Path("userId") userId: Long,
    ): EmptyDataResponse
}
