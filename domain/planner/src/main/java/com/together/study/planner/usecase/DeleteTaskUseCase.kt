package com.together.study.planner.usecase

import com.together.study.planner.repository.PlannerTaskRepository
import javax.inject.Inject

class DeleteTaskUseCase@Inject constructor(
    private val repository: PlannerTaskRepository,
) {
    suspend operator fun invoke(taskId: Long): Result<Unit> {
        return repository.deleteTask(taskId)
    }
}
