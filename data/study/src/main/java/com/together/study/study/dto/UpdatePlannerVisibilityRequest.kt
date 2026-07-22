package com.together.study.study.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdatePlannerVisibilityRequest(
    @SerialName("isPlannerVisible") val isPlannerVisible: Boolean,
)
