package com.together.study.auth.datasource

import com.together.study.auth.dto.KakaoLoginResponse
import com.together.study.auth.service.AuthService
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val authService: AuthService,
) {
    suspend fun postLoginKakao(accessToken: String) =
        authService.postLoginKakao(accessToken).response
}
