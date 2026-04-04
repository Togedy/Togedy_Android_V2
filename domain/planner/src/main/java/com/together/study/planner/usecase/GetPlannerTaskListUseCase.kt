package com.together.study.planner.usecase

import com.together.study.planner.model.PlannerSubject
import com.together.study.planner.repository.PlannerTaskRepository
import javax.inject.Inject

class GetPlannerTaskListUseCase @Inject constructor(
    private val repository: PlannerTaskRepository,
) {
    suspend operator fun invoke(date: String): Result<List<PlannerSubject>> {
        return repository.getPlannerTaskList(date)
    }
}
