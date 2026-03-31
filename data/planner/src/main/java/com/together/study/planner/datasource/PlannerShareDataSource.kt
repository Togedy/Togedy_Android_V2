package com.together.study.planner.datasource

import com.together.study.planner.service.PlannerShareService
import javax.inject.Inject

class PlannerShareDataSource @Inject constructor(
    private val plannerShareService: PlannerShareService,
) {
    suspend fun getShareInfo(date: String) = plannerShareService.getShareInfo(date)
}
