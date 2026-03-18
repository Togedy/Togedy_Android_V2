package com.together.study.planner.usecase

import com.together.study.planner.repository.PlannerRepository

class GetMonthlyHeatmapUseCase(
    private val repository: PlannerRepository,
) {
    suspend operator fun invoke(month: String): Result<List<Int>> {
        return repository.getMonthlyHeatmap(month)
    }
}
