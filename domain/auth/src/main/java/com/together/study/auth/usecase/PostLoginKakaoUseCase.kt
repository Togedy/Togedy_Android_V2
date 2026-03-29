package com.together.study.auth.usecase

import com.together.study.auth.model.KakaoLogin
import com.together.study.auth.repository.AuthRepository
import javax.inject.Inject

class PostLoginKakaoUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(token: String): Result<KakaoLogin> {
        return authRepository.postLoginKakao(token)
    }
}
