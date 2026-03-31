package com.together.study.user.usecase

import com.together.study.user.repository.UserRepository
import javax.inject.Inject

class UpdateMarketingConsentUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(marketingConsented: Boolean): Result<Unit> {
        return userRepository.updateMarketingConsent(marketingConsented)
    }
}
