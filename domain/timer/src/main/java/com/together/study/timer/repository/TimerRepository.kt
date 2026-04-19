package com.together.study.timer.repository

import com.together.study.timer.model.RunningTimer
import com.together.study.timer.model.SubjectTimer
import com.together.study.timer.model.Timer

interface TimerRepository {
    suspend fun getTotalStudyTimer(): Result<Long>
    suspend fun getRunningTimer(): Result<RunningTimer>
    suspend fun getSummaryTimer(): Result<List<SubjectTimer>>
    suspend fun stopTimer(timerId: Long): Result<Timer>
    suspend fun startTimer(subjectId: Long): Result<Timer>
}
