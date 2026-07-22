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
    @SerialName("studySubjectName") val studySubjectName: String,
    @SerialName("taskList") val taskList: List<TaskResponse>,
)

@Serializable
data class TaskResponse(
    @SerialName("taskName") val taskName: String,
    @SerialName("isChecked") val isChecked: Boolean,
)
