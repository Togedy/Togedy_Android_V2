package com.together.study.timer.usecase

import com.together.study.timer.repository.TimerRepository
import javax.inject.Inject

class StopTimerUseCase @Inject constructor(
    private val timerRepository: TimerRepository,
) {
    suspend operator fun invoke(timerId: Long) =
        timerRepository.stopTimer(timerId)
}
