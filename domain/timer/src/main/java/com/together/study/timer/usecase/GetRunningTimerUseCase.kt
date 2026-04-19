package com.together.study.timer.usecase

import com.together.study.timer.model.RunningTimer
import com.together.study.timer.repository.TimerRepository
import javax.inject.Inject

class GetRunningTimerUseCase @Inject constructor(
    private val timerRepository: TimerRepository,
) {
    suspend operator fun invoke(): Result<RunningTimer> {
        return timerRepository.getRunningTimer()
    }
}
