package com.together.study.auth.repository

import com.together.study.auth.model.KakaoLogin

interface AuthRepository {
    suspend fun postLoginKakao(accessToken: String) : Result<KakaoLogin>
    suspend fun postLogout(): Result<Unit>
}
