package com.together.study.planner.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class SubjectItemRequest(
    val subjectName: String?,
    val subjectColor: String?,
)
