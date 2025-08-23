package com.together.study.search.repositoryimpl

import com.together.study.search.datasource.UserScheduleDataSource
import com.together.study.search.model.UnivSchedule
import com.together.study.search.repository.UnivScheduleRepository
import javax.inject.Inject

class UnivScheduleRepositoryImpl @Inject constructor(
    private val univScheduleDataSource: UserScheduleDataSource,
) : UnivScheduleRepository {
    override suspend fun getUnivScheduleList(): Result<List<UnivSchedule>> {
        runCatching {
            val response = univScheduleDataSource.getUnivSchedule(request = univSche).response
            response.toDomain()
        }
    }
}
