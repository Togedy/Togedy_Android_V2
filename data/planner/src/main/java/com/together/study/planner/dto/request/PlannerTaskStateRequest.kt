package com.together.study.planner.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class PlannerTaskStateRequest(
    val isChecked: Boolean,
)
