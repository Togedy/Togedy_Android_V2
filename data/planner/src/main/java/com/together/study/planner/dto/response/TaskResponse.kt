package com.together.study.planner.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class TaskResponse(
    val taskId: Long?,
    val taskName: String,
    val isChecked: Boolean,
)
