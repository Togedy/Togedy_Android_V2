package com.together.study.calendar.datasouce

import com.together.study.calendar.dto.UserScheduleRequest
import com.together.study.calendar.service.UserScheduleService
import javax.inject.Inject

class UserScheduleDataSource @Inject constructor(
    private val userScheduleService: UserScheduleService,
) {
    suspend fun postUserSchedule(request: UserScheduleRequest) =
        userScheduleService.postUserSchedule(request)

    suspend fun getUserSchedule(request: Long) =
        userScheduleService.getUserSchedule(request)

    suspend fun patchUserSchedule(userScheduleId: Long, request: UserScheduleRequest) =
        userScheduleService.patchUserSchedule(
            userScheduleId = userScheduleId,
            userScheduleRequest = request
        )

    suspend fun deleteUserSchedule(request: Long) =
        userScheduleService.deleteUserSchedule(request)
}
