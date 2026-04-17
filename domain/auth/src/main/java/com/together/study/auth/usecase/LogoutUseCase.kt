package com.together.study.auth.usecase

import com.together.study.auth.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(): Result<Unit> {
        return authRepository.postLogout()
    }
}
