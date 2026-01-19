package com.together.study.search.service

import com.together.study.remote.model.BaseResponse
import com.together.study.remote.model.EmptyDataResponse
import com.together.study.search.dto.UnivDetailScheduleAddRequest
import com.together.study.search.dto.UnivDetailScheduleResponse
import com.together.study.search.dto.UnivScheduleListResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UnivScheduleService {
    @GET("calendars/universities")
    suspend fun getUnivSchedule(
        @Query("name") name: String,
        @Query("admission-type") admissionType : String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): BaseResponse<UnivScheduleListResponse>

    @GET("calendars/universities/{universityId}/schedule")
    suspend fun getUnivDetailSchedule(
        @Path("universityId") universityId: Int,
    ): BaseResponse<UnivDetailScheduleResponse>

    @DELETE("calendars/universities/{universityAdmissionMethodId}")
    suspend fun deleteUnivDetailSchedule(
        @Path("universityAdmissionMethodId") universityAdmissionMethodId: Int,
    ): EmptyDataResponse

    @POST("calendars/universities")
    suspend fun addUnivDetailSchedule(
        @Body univDetailScheduleAddRequest: UnivDetailScheduleAddRequest,
    ): EmptyDataResponse
}
