package com.together.study.planner.usecase

import com.together.study.planner.model.TaskItem
import com.together.study.planner.repository.PlannerTaskRepository
import javax.inject.Inject

class UpdateTaskContentUseCase @Inject constructor(
    private val repository: PlannerTaskRepository,
) {
    suspend operator fun invoke(
        taskId: Long?,
        taskName: String?,
        subjectId: Long,
        date: String
    ): Result<Int> {
        return repository.updateTaskContent(
            TaskItem(
                taskId = taskId,
                taskName = taskName
            ),
            subjectId = subjectId,
            date = date,
        )
    }
}
