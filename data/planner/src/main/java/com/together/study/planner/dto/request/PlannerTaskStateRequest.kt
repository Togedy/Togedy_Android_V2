package com.together.study.planner.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlannerTaskStateRequest(
    @SerialName("isChecked") val isChecked: Boolean,
)
