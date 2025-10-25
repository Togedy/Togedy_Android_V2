package com.together.study.study.service

import com.together.study.remote.model.BaseResponse
import com.together.study.study.dto.ExploreStudyInfoResponse
import com.together.study.study.dto.MyStudyInfoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface StudyExploreService {
    @GET("users/me/studies")
    suspend fun getMyStudyInfo(): BaseResponse<MyStudyInfoResponse>

    @GET("studies")
    suspend fun getExploreStudyItems(
        @Query("tag") tag: List<String>?,
        @Query("filter") filter: String,
        @Query("joinable") joinable: Boolean,
        @Query("challenge") challenge: Boolean,
        @Query("page") page: Int?,
        @Query("size") size: Int?,
    ): BaseResponse<ExploreStudyInfoResponse>
}
