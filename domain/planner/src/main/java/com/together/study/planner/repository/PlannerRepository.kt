package com.together.study.planner.repository

import com.together.study.planner.model.DailyPlannerInfo
import com.together.study.planner.model.DailyStatistics
import com.together.study.planner.model.TimeTable

interface PlannerRepository {
    suspend fun postDailyPlannerImage(date: String): Result<Unit>
    suspend fun getDailyPlannerInfo(date: String): Result<DailyPlannerInfo>
    suspend fun getDailyStatistics(date: String): Result<DailyStatistics>
    suspend fun getDailyTimetable(date: String): Result<TimeTable>
    suspend fun getMonthlyHeatmap(month: String): Result<List<Int>>
}
