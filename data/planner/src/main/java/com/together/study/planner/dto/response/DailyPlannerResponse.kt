package com.together.study.planner.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class DailyPlannerTaskResponse(
    val dailyPlanner: List<PlannerSubjectResponse>,
)