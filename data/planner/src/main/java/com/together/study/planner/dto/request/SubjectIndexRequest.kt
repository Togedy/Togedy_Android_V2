package com.together.study.planner.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class SubjectIndexRequest(
    val prevId: Long?,
    val nextId: Long?,
)
