package com.together.study.planner.datasource

import com.together.study.planner.dto.request.PlannerTaskRequest
import com.together.study.planner.dto.request.PlannerTaskStateRequest
import com.together.study.planner.service.PlannerTaskService
import javax.inject.Inject

class PlannerTaskDataSource @Inject constructor(
    private val plannerTaskService: PlannerTaskService,
) {
    suspend fun getPlannerTaskList(date: String) =
        plannerTaskService.getPlannerTaskList(date)

    suspend fun updateTaskContent(
        taskId: Long?,
        subjectId: Long,
        name: String?,
        date: String,
    ) = plannerTaskService.updateTaskContent(
        request = PlannerTaskRequest(
            taskId = taskId,
            studySubjectId = subjectId,
            name = name,
            date = date,
        )
    )

    suspend fun deleteTask(taskId: Long) =
        plannerTaskService.deleteTask(taskId)

    suspend fun updateTaskChecked(taskId: Long, isChecked: Boolean) =
        plannerTaskService.updateTaskChecked(
            taskId = taskId,
            request = PlannerTaskStateRequest(isChecked)
        )

}
