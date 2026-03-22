package com.together.study.planner.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class DailyPlannerResponse(
    val dailyPlanner: List<PlannerSubjectResponse>,
)