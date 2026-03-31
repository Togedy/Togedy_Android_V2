package com.together.study.planner.usecase

import com.together.study.planner.model.DailyPlannerInfo
import com.together.study.planner.repository.PlannerRepository
import javax.inject.Inject

class GetDailyPlannerInfoUseCase @Inject constructor(
    private val repository: PlannerRepository,
) {
    suspend operator fun invoke(date: String): Result<DailyPlannerInfo> {
        return repository.getDailyPlannerInfo(date)
    }
}
