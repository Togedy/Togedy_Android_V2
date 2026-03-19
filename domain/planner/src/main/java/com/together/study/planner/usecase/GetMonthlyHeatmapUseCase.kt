package com.together.study.planner.usecase

import com.together.study.planner.repository.PlannerRepository

class GetMonthlyHeatmapUseCase(
    private val repository: PlannerRepository,
) {
    suspend operator fun invoke(year: Int, month: Int): Result<List<Int>> {
        return repository.getMonthlyHeatmap("$year-${month.toString().padStart(2, '0')}")
    }
}
