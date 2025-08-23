package com.together.study.search.service

import com.together.study.remote.model.BaseResponse
import com.together.study.search.dto.UnivScheduleResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UnivScheduleService {
    @GET("calendars/universities")
    suspend fun getUnivSchedule(
        @Query("name") name: String,
        @Query("admission-type") admissionType : String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): BaseResponse<List<UnivScheduleResponse>>
}
