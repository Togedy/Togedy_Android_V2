package com.together.study.calendar.repository

import com.together.study.calendar.model.UserSchedule

interface UserScheduleRepository {
    suspend fun postUserSchedule(userSchedule: UserSchedule): Result<Boolean>
    suspend fun getUserSchedule(userScheduleId: Long): Result<UserSchedule>
    suspend fun patchUserSchedule(request: Pair<Long, UserSchedule>): Result<Boolean>
    suspend fun deleteUserSchedule(userScheduleId: Long): Result<Boolean>
}
