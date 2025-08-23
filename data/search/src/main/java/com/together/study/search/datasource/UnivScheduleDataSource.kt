package com.together.study.search.datasource

import com.together.study.search.service.UnivScheduleService
import javax.inject.Inject

class UnivScheduleDataSource @Inject constructor(
    private val univScheduleService: UnivScheduleService,
) {
    suspend fun getUnivSchedule(name: String, admissionType: String, page: Int, size: Int) =
        univScheduleService.getUnivSchedule(name, admissionType, page, size)
}
