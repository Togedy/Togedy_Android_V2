package com.together.study.timer.repositoryimpl

import com.together.study.timer.datasource.TimerDataSource
import com.together.study.timer.mapper.toDomain
import com.together.study.timer.mapper.toDomainList
import com.together.study.timer.model.RunningTimer
import com.together.study.timer.model.SubjectTimer
import com.together.study.timer.model.Timer
import com.together.study.timer.repository.TimerRepository
import javax.inject.Inject

class TimerRepositoryImpl @Inject constructor(
    private val timerDataSource: TimerDataSource,
) : TimerRepository {
    override suspend fun getTotalStudyTimer(): Result<Long> =
        runCatching {
            val baseResponse = timerDataSource.getTotalStudyTimer()
            if (!baseResponse.isSuccess) {
                throw Exception("API returned isSuccess=false")
            }
            baseResponse.response.studyTime
        }

    override suspend fun getRunningTimer(): Result<RunningTimer> =
        runCatching {
            val response = timerDataSource.getRunningTimer().response
            response.toDomain()
        }


    override suspend fun getSummaryTimer(): Result<List<SubjectTimer>> =
        runCatching {
            val response = timerDataSource.getSummaryTimer().response
            response.toDomainList()
        }

    override suspend fun stopTimer(timerId: Long): Result<Timer> =
        runCatching {
            val response = timerDataSource.stopTimer(timerId).response
            response.toDomain()
        }

    override suspend fun startTimer(subjectId: Long): Result<Timer> =
        runCatching {
            val response = timerDataSource.startTimer(subjectId).response
            response.toDomain()
        }
}
