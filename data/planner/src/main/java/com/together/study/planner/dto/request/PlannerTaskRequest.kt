package com.together.study.planner.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class PlannerTaskRequest(
    val taskId: Long?,
    val studySubjectId: Long,
    val name: String,
    val date: String,
)
