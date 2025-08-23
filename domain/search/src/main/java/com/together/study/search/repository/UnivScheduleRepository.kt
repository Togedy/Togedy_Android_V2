package com.together.study.search.repository

import com.together.study.search.model.UnivSchedule

interface UnivScheduleRepository {
    suspend fun getUnivScheduleList(): Result<List<UnivSchedule>>
}
