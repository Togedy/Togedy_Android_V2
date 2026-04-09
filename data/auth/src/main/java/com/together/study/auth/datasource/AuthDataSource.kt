package com.together.study.auth.datasource

import com.together.study.auth.service.AuthService
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val authService: AuthService,
) {
    suspend fun postLoginKakao(accessToken: String) =
        authService.postLoginKakao(accessToken).response

    suspend fun postLogout() {
        val response = authService.postLogout()
        if (!response.isSuccessful) {
            throw Exception("로그아웃 실패: ${response.code()}")
        }
    }
}
