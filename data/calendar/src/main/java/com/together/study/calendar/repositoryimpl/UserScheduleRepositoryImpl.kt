package com.together.study.calendar.repositoryimpl

import com.together.study.calendar.datasouce.UserScheduleDataSource
import com.together.study.calendar.mapper.toData
import com.together.study.calendar.mapper.toDomain
import com.together.study.calendar.model.UserSchedule
import com.together.study.calendar.repository.UserScheduleRepository
import javax.inject.Inject

class UserScheduleRepositoryImpl @Inject constructor(
    private val userScheduleDataSource: UserScheduleDataSource,
) : UserScheduleRepository {
    override suspend fun postUserSchedule(userSchedule: UserSchedule): Result<Boolean> =
        runCatching {
            userScheduleDataSource.postUserSchedule(request = userSchedule.toData()).response
        }

    override suspend fun getUserSchedule(userScheduleId: Long): Result<UserSchedule> =
        runCatching {
            val response = userScheduleDataSource.getUserSchedule(request = userScheduleId).response
            response.toDomain()
        }

    override suspend fun patchUserSchedule(
        userScheduleId: Long,
        request: UserSchedule,
    ): Result<Boolean> =
        runCatching {
            userScheduleDataSource.patchUserSchedule(
                userScheduleId = userScheduleId,
                request = request.toData()
            ).response
        }

    override suspend fun deleteUserSchedule(userScheduleId: Long): Result<Boolean> =
        runCatching {
            userScheduleDataSource.deleteUserSchedule(request = userScheduleId).response
        }
}
