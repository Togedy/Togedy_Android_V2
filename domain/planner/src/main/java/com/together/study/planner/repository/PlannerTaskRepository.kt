package com.together.study.planner.repository

import com.together.study.planner.model.PlannerSubject
import com.together.study.planner.model.TaskItem

interface PlannerTaskRepository {
    suspend fun getPlannerTaskList(date: String): Result<List<PlannerSubject>>
    suspend fun updateTaskContent(task: TaskItem, subjectId: Long, date: String?): Result<Long>
    suspend fun deleteTask(taskId: Long): Result<Unit>
    suspend fun updateTaskChecked(taskId: Long, isChecked: Boolean): Result<Unit>
}
