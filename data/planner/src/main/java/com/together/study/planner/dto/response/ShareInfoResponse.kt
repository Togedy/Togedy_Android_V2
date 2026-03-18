package com.together.study.planner.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class ShareInfoResponse(
    val date: String,
    val hasDday: Boolean,
    val userScheduleName: String?,
    val remainingDays: Int?,
    val totalStudyTime: String,
    val image: String?,
    val plannerItemList: List<PlannerSubjectResponse>,
    val timeTableList: List<TimeTableResponse>,
)
