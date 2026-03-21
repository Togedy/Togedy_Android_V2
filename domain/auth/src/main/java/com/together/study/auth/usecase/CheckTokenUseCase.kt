package com.together.study.auth.usecase

import com.together.study.auth.repository.TokenRepository
import javax.inject.Inject

class CheckTokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository,
) {
    suspend operator fun invoke(): Boolean {
        val accessToken = tokenRepository.getAccessToken()
        val refreshToken = tokenRepository.getRefreshToken()
        return !accessToken.isNullOrEmpty() && !refreshToken.isNullOrEmpty()
    }
}
