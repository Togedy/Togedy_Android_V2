package com.together.study.calendar.service

import com.together.study.calendar.dto.UserScheduleRequest
import com.together.study.calendar.dto.UserScheduleResponse
import com.together.study.remote.model.BaseResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface UserScheduleService {
    @POST("calendars/users")
    suspend fun postUserSchedule(
        @Body userScheduleRequest: UserScheduleRequest,
    ): BaseResponse<Boolean>

    @GET("calendars/users/{userScheduleId}")
    suspend fun getUserSchedule(
        @Path("userScheduleId") userScheduleId: Long,
    ): BaseResponse<UserScheduleResponse>

    @PATCH("calendars/users/{userScheduleId}")
    suspend fun patchUserSchedule(
        @Path("userScheduleId") userScheduleId: Long,
        @Body userScheduleRequest: UserScheduleRequest,
    ): BaseResponse<Boolean>

    @DELETE("calendars/users/{userScheduleId}")
    suspend fun deleteUserSchedule(
        @Path("userScheduleId") userScheduleId: Long,
    ): BaseResponse<Boolean>
}
