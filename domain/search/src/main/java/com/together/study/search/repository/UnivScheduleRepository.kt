package com.together.study.search.repository

import com.together.study.search.model.UnivSchedule

interface UnivScheduleRepository {
    suspend fun getUnivScheduleList(
        name: String,
        admissionType: String,
        page: Int,
        size: Int
    ): Result<List<UnivSchedule>>
}
