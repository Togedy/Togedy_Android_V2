package com.together.study.user.usecase

import com.together.study.user.repository.UserRepository
import javax.inject.Inject

class CompleteOnboardingUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(nickname: String, birthDate: String): Result<Unit> {
        return userRepository.completeOnboarding(nickname, birthDate)
    }
}
