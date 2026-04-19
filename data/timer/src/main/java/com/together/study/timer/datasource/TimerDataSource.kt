package com.together.study.timer.datasource

import com.together.study.timer.service.TimerService
import javax.inject.Inject

class TimerDataSource @Inject constructor(
    private val timerService: TimerService,
) {
    suspend fun getTotalStudyTimer() = timerService.getTotalStudyTimer()
    suspend fun getRunningTimer() = timerService.getRunningTimer()
    suspend fun getSummaryTimer() = timerService.getSummaryTimer()
    suspend fun stopTimer(timerId: Long) = timerService.stopTimer(timerId)
    suspend fun startTimer(subjectId: Long) = timerService.startTimer(subjectId)
}
