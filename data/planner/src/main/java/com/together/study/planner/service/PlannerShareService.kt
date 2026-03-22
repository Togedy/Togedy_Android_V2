package com.together.study.planner.service

import com.together.study.planner.dto.response.ShareInfoResponse
import com.together.study.remote.model.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PlannerShareService {

    @GET("planners/daily/share")
    suspend fun getShareInfo(
        @Query("date") date: String,
    ): BaseResponse<ShareInfoResponse>
}
