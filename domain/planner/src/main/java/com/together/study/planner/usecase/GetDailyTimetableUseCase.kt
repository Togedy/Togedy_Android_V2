package com.together.study.planner.usecase

import com.together.study.planner.model.TimeTable
import com.together.study.planner.repository.PlannerRepository

class GetDailyTimetableUseCase(
    private val repository: PlannerRepository,
) {
    suspend operator fun invoke(date: String): Result<List<TimeTable>> {
        return repository.getDailyTimetable(date)
    }
}
