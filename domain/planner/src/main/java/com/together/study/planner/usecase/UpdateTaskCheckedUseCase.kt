package com.together.study.planner.usecase

import com.together.study.planner.repository.PlannerTaskRepository
import javax.inject.Inject

class UpdateTaskCheckedUseCase @Inject constructor(
    private val repository: PlannerTaskRepository,
) {
    suspend operator fun invoke(taskId: Long, isChecked: Boolean): Result<Unit> {
        return repository.updateTaskChecked(taskId, isChecked)
    }
}
