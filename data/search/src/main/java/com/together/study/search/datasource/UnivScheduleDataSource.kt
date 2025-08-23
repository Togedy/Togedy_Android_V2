package com.together.study.search.datasource

import com.together.study.search.dto.UnivScheduleRequest
import com.together.study.search.service.UnivScheduleService
import javax.inject.Inject

class UserScheduleDataSource @Inject constructor(
    private val univScheduleService: UnivScheduleService,
) {
    suspend fun getUnivSchedule(request: UnivScheduleRequest) =
        univScheduleService.getUserSchedule(request)
}
