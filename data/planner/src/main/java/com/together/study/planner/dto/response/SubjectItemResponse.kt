package com.together.study.planner.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class SubjectItemResponse(
    val subjectId: Long,
    val subjectName: String,
    val subjectColor: String,
    val orderIndex: Long,
)
