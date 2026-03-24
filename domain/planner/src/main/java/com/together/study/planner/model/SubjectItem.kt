package com.together.study.planner.model

data class SubjectItem(
    val subjectId: Long?,
    val subjectName: String,
    val subjectColor: String,
    val orderIndex: Long? = null,
)
