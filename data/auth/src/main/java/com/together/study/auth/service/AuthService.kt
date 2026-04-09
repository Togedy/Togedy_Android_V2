package com.together.study.auth.service

import com.together.study.auth.dto.KakaoLoginResponse
import com.together.study.remote.model.BaseResponse
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("auth/kakao")
    suspend fun postLoginKakao(
        @Header("Kakao-Access-Token") kakaoAccessToken: String
    ): BaseResponse<KakaoLoginResponse>

    @POST("auth/logout")
    suspend fun postLogout(): Response<Unit>
}
