package com.together.study.planner.repositoryimpl

import com.together.study.planner.datasource.PlannerShareDataSource
import com.together.study.planner.mapper.toDomain
import com.together.study.planner.model.ShareInfo
import com.together.study.planner.repository.PlannerShareRepository
import javax.inject.Inject

class PlannerShareRepositoryImpl @Inject constructor(
    private val plannerShareDataSource: PlannerShareDataSource,
) : PlannerShareRepository {
    override suspend fun getShareInfo(date: String): Result<ShareInfo> = runCatching {
        val response = plannerShareDataSource.getShareInfo(date).response
        response.toDomain()
    }
}
