package com.together.study.planner.usecase

import com.together.study.planner.model.DailyStatistics
import com.together.study.planner.repository.PlannerRepository

class GetDailyStatisticsUseCase(
    private val repository: PlannerRepository,
) {
    suspend operator fun invoke(date: String): Result<DailyStatistics> {
        return repository.getDailyStatistics(date)
    }
}
