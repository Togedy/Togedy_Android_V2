package com.together.study.timer.usecase

import com.together.study.timer.model.Timer
import com.together.study.timer.repository.TimerRepository
import javax.inject.Inject

class StartTimerUseCase @Inject constructor(
    private val timerRepository: TimerRepository,
) {
    suspend operator fun invoke(subjectId: Long): Result<Timer> {
        return timerRepository.startTimer(subjectId)
    }
}
