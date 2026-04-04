package com.together.study.planner.datasource

import com.together.study.planner.service.PlannerService
import javax.inject.Inject

class PlannerDataSource @Inject constructor(
    private val plannerService: PlannerService,
) {
    suspend fun postDailyPlannerImage(date: String) =
        plannerService.postDailyPlannerImage(date)

    suspend fun getDailyPlannerInfo(date: String) =
        plannerService.getDailyPlannerInfo(date)

    suspend fun getDailyStatistics(date: String) =
        plannerService.getDailyStatistics(date)

    suspend fun getDailyTimetable(date: String) =
        plannerService.getDailyTimetable(date)

    suspend fun getMonthlyHeatmap(month: String) =
        plannerService.getMonthlyHeatmap(month)
}
