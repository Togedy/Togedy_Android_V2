package com.together.study.planner.mapper

import com.together.study.planner.dto.response.DailyPlannerTaskListResponse
import com.together.study.planner.model.PlannerSubject

fun DailyPlannerTaskListResponse.toDomain(): List<PlannerSubject> {
    return this.dailyPlanner.map { it.toDomain() }
}