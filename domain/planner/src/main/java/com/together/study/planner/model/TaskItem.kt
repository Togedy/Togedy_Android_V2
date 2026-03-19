package com.together.study.planner.model

data class TaskItem(
    val taskId: Long? = null,
    val taskName: String? = null,
    val isChecked: Boolean = false,
)
