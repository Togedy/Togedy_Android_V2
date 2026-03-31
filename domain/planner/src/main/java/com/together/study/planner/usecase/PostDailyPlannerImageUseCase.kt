package com.together.study.planner.usecase

import com.together.study.planner.repository.PlannerRepository
import javax.inject.Inject

class PostDailyPlannerImageUseCase @Inject constructor(
    private val repository: PlannerRepository,
) {
    suspend operator fun invoke(date: String): Result<Unit> {
        return repository.postDailyPlannerImage(date)
    }
}
