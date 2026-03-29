package com.together.study.planner.model

import java.util.UUID

data class TaskItem(
    val taskId: Long? = null,
    val taskName: String? = null,
    val isChecked: Boolean = false,
    val tempId: String = UUID.randomUUID().toString(),
)
