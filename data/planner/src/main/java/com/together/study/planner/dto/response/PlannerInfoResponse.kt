package com.together.study.planner.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class PlannerInfoResponse(
    val date: String,
    val hasDday: Boolean,
    val userScheduleName: String?,
    val remainingDays: Int?,
    val totalStudyTime: String,
    val plannerImage: String?,
)
