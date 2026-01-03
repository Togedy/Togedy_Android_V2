package com.together.study.study.service

import com.together.study.remote.model.BaseResponse
import com.together.study.study.dto.StudyDuplicateResponse
import com.together.study.remote.model.EmptyDataResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface StudyUpdateService {
    @Multipart
    @POST("studies")
    suspend fun createStudy(
        @Part("challengeGoalTime") challengeGoalTime: RequestBody?,
        @Part("studyName") studyName: RequestBody,
        @Part("studyDescription") studyDescription: RequestBody?,
        @Part("studyMemberLimit") studyMemberLimit: RequestBody,
        @Part("studyTag") studyTag: RequestBody,
        @Part("studyPassword") studyPassword: RequestBody?,
        @Part studyImage: MultipartBody.Part?,
    ): EmptyDataResponse

    @GET("studies/duplicate")
    suspend fun checkStudyNameDuplicate(
        @Query("name") name: String,
    ): BaseResponse<StudyDuplicateResponse>

    @Multipart
    @PATCH("studies/{studyId}/information")
    suspend fun updateStudy(
        @Path("studyId") studyId: Long,
        @Part("challengeGoalTime") challengeGoalTime: RequestBody?,
        @Part("studyName") studyName: RequestBody,
        @Part("studyDescription") studyDescription: RequestBody?,
        @Part("studyMemberLimit") studyMemberLimit: RequestBody,
        @Part("studyTag") studyTag: RequestBody,
        @Part("studyPassword") studyPassword: RequestBody?,
        @Part studyImage: MultipartBody.Part?,
    ): EmptyDataResponse
}

