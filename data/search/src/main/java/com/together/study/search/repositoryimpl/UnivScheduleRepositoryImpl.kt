package com.together.study.search.repositoryimpl


import com.together.study.search.datasource.UnivScheduleDataSource
import com.together.study.search.mapper.toDomain
import com.together.study.search.model.UnivSchedule
import com.together.study.search.repository.UnivScheduleRepository
import javax.inject.Inject

class UnivScheduleRepositoryImpl @Inject constructor(
    private val univScheduleDataSource: UnivScheduleDataSource,
) : UnivScheduleRepository {
    override suspend fun getUnivScheduleList(
        name: String,
        admissionType: String,
        page: Int,
        size: Int
    ): Result<List<UnivSchedule>> =
        runCatching {
            val response = univScheduleDataSource.getUnivSchedule(name, admissionType, page, size).response
            response.map { it.toDomain() }
        }

}
