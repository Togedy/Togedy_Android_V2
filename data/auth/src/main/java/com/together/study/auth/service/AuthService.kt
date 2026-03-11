package com.together.study.auth.service

import com.together.study.auth.dto.KakaoLoginResponse
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("auth/kakao")
    suspend fun loginWithKakaoAccessToken(
        @Header("Kakao-Access-Token") kakaoAccessToken: String
    ) : KakaoLoginResponse
}
