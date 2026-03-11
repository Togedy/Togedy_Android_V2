package com.together.study.auth.usecase

import com.together.study.auth.model.KakaoLogin
import com.together.study.auth.repository.AuthRepository
import javax.inject.Inject

class KakaoLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend fun invoke(token: String): Result<KakaoLogin> {
        return authRepository.loginWithKakao(token)
    }
}