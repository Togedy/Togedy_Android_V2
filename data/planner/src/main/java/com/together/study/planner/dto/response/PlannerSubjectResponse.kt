package com.together.study.planner.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class PlannerSubjectResponse(
    val subjectId: Long,
    val subjectName: String,
    val subjectColor: String,
    val totalTaskCount: Int? = null,
    val checkedTaskCount: Int? = null,
    val subjectStudyTime: String = "",
    val taskList: List<TaskResponse>,
)
