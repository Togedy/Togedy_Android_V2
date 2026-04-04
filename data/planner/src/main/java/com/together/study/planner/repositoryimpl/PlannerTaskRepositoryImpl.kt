package com.together.study.planner.repositoryimpl

import com.together.study.planner.datasource.PlannerTaskDataSource
import com.together.study.planner.mapper.toDomain
import com.together.study.planner.model.PlannerSubject
import com.together.study.planner.model.TaskItem
import com.together.study.planner.repository.PlannerTaskRepository
import javax.inject.Inject

class PlannerTaskRepositoryImpl @Inject constructor(
    private val plannerTaskDataSource: PlannerTaskDataSource,
) : PlannerTaskRepository {
    override suspend fun getPlannerTaskList(date: String): Result<List<PlannerSubject>> =
        runCatching {
            val response = plannerTaskDataSource.getPlannerTaskList(date).response
            response.toDomain()
        }

    override suspend fun updateTaskContent(
        task: TaskItem,
        subjectId: Long,
        date: String?,
    ): Result<Long> =
        runCatching {
            plannerTaskDataSource.updateTaskContent(
                taskId = task.taskId,
                subjectId = subjectId,
                name = task.taskName,
                date = date,
            ).response
        }

    override suspend fun deleteTask(taskId: Long): Result<Unit> =
        runCatching { plannerTaskDataSource.deleteTask(taskId) }

    override suspend fun updateTaskChecked(taskId: Long, isChecked: Boolean): Result<Unit> =
        runCatching {
            plannerTaskDataSource.updateTaskChecked(taskId, isChecked).response
        }
}
