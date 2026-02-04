package com.together.study.planner.model

data class PlannerItem(
    val subjectName: String,
    val subjectColor: String,
    val totalTaskCount: Int,
    val checkedTaskCount: Int,
    val taskList: List<TaskItem>,
)
