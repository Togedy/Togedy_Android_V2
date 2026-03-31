package com.together.study.planner.repository

import com.together.study.planner.model.ShareInfo

interface PlannerShareRepository {
    suspend fun getShareInfo(date: String): Result<ShareInfo>
}
