package com.together.study.timer.usecase

import com.together.study.timer.model.SubjectTimer
import com.together.study.timer.repository.TimerRepository
import javax.inject.Inject

class GetSummaryTimerUseCase @Inject constructor(
    private val timerRepository: TimerRepository,
) {
    suspend operator fun invoke(): Result<List<SubjectTimer>> {
        return timerRepository.getSummaryTimer()
    }
}
