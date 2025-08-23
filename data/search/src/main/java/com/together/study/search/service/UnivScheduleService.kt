package com.together.study.search.service

import com.together.study.remote.model.BaseResponse
import com.together.study.search.dto.UnivScheduleRequest
import com.together.study.search.dto.UnivScheduleResponse
import retrofit2.http.Body
import retrofit2.http.GET

interface UnivScheduleService {
    @GET("calendars/universities")
    suspend fun getUserSchedule(
        @Body univScheduleRequest: UnivScheduleRequest,
    ): BaseResponse<List<UnivScheduleResponse>>
}
