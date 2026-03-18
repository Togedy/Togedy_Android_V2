package com.together.study.planner.usecase

import com.together.study.planner.model.ShareInfo
import com.together.study.planner.repository.PlannerShareRepository

class GetShareInfoUseCase(
    private val repository: PlannerShareRepository,
) {
    suspend operator fun invoke(date: String): Result<ShareInfo> {
        return repository.getShareInfo(date)
    }
}
