package com.together.study.study.service

import com.together.study.remote.model.BaseResponse
import com.together.study.study.dto.JoinStudyRequest
import com.together.study.study.dto.StudyAttendanceResponse
import com.together.study.study.dto.StudyDetailInfoResponse
import com.together.study.study.dto.StudyMemberResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface StudyDetailService {
    @GET("studies/{studyId}")
    suspend fun getStudyDetailInfo(
        @Path("studyId") studyId: Long,
    ): BaseResponse<StudyDetailInfoResponse>

    @GET("studies/{studyId}/members")
    suspend fun getStudyMembers(
        @Path("studyId") studyId: Long,
    ): BaseResponse<List<StudyMemberResponse>>

    @GET("studies/{studyId}/members/attendance")
    suspend fun getStudyAttendance(
        @Path("studyId") studyId: Long,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String,
    ): BaseResponse<List<StudyAttendanceResponse>>

    @POST("studies/{studyId}/members")
    suspend fun postStudyJoin(
        @Path("studyId") studyId: Long,
        @Body body: JoinStudyRequest,
    ): BaseResponse<Unit>
}
