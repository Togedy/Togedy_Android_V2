package com.together.study.planner.usecase

import com.together.study.planner.model.DailyPlannerInfo
import com.together.study.planner.repository.PlannerRepository

class GetDailyPlannerInfoUseCase(
    private val repository: PlannerRepository,
) {
    suspend operator fun invoke(date: String): Result<DailyPlannerInfo> {
        return repository.getDailyPlannerInfo(date)
    }
}
