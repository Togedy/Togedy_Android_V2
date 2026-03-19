package com.together.study.planner.usecase

import com.together.study.planner.model.DailyStatistics
import com.together.study.planner.repository.PlannerRepository
import javax.inject.Inject

class GetDailyStatisticsUseCase @Inject constructor(
    private val repository: PlannerRepository,
) {
    suspend operator fun invoke(date: String): Result<DailyStatistics> {
        return repository.getDailyStatistics(date)
    }
}
