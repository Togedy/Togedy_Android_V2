package com.together.study.planner.model

data class PlannerSubject(
    val subjectId: Long? = null,
    val subjectName: String = "",
    val subjectColor: String = "",
    val subjectStudyTime: String = "",
    val tasks: List<Todo> = emptyList(),
)
