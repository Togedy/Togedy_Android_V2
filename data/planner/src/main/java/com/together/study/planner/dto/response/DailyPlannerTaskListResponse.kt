package com.together.study.planner.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class DailyPlannerTaskListResponse(
    val dailyPlanner: List<PlannerSubjectResponse>,
)