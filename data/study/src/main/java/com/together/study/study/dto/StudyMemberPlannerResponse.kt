package com.together.study.study.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudyMemberPlannerResponse(
    @SerialName("isMyPlanner") val isMyPlanner: Boolean,
    @SerialName("isPlannerVisible") val isPlannerVisible: Boolean,
    @SerialName("completedCount") val completedPlanCount: Int?,
    @SerialName("totalPlanCount") val totalPlanCount: Int?,
    @SerialName("dailyPlanner") val dailyPlanner: List<DailyPlannerResponse>?,
)

@Serializable
data class DailyPlannerResponse(
    @SerialName("studyCategoryName") val studyCategoryName: String,
    @SerialName("planList") val planList: List<PlanResponse>,
)

@Serializable
data class PlanResponse(
    @SerialName("planName") val planName: String,
    @SerialName("planStatus") val planStatus: String,
)
