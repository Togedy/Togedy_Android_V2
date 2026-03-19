package com.together.study.planner.usecase

import com.together.study.planner.model.ShareInfo
import com.together.study.planner.repository.PlannerShareRepository
import javax.inject.Inject

class GetShareInfoUseCase @Inject constructor(
    private val repository: PlannerShareRepository,
) {
    suspend operator fun invoke(date: String): Result<ShareInfo> {
        return repository.getShareInfo(date)
    }
}
