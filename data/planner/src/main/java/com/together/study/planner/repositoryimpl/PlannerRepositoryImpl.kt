package com.together.study.planner.repositoryimpl

import com.together.study.planner.datasource.PlannerDataSource
import com.together.study.planner.mapper.toDomain
import com.together.study.planner.model.DailyPlannerInfo
import com.together.study.planner.model.DailyStatistics
import com.together.study.planner.model.TimeTable
import com.together.study.planner.repository.PlannerRepository
import javax.inject.Inject

class PlannerRepositoryImpl @Inject constructor(
    private val plannerDataSource: PlannerDataSource,
) : PlannerRepository {
    override suspend fun postDailyPlannerImage(date: String): Result<Unit> =
        runCatching {
            plannerDataSource.postDailyPlannerImage(date)
        }

    override suspend fun getDailyPlannerInfo(date: String): Result<DailyPlannerInfo> =
        runCatching {
            val response = plannerDataSource.getDailyPlannerInfo(date).response
            response.toDomain()
        }

    override suspend fun getDailyStatistics(date: String): Result<DailyStatistics> =
        runCatching {
            val response = plannerDataSource.getDailyStatistics(date).response
            response.toDomain()
        }

    override suspend fun getDailyTimetable(date: String): Result<List<TimeTable>> =
        runCatching {
            val response = plannerDataSource.getDailyTimetable(date).response
            response.toDomain()
        }

    override suspend fun getMonthlyHeatmap(month: String): Result<List<Int>> =
        runCatching {
            val response = plannerDataSource.getMonthlyHeatmap(month).response
            response.heatmapList
        }
}
