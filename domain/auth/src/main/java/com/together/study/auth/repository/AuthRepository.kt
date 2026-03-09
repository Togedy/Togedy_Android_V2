package com.together.study.auth.repository

import com.together.study.auth.model.KakaoLogin

interface AuthRepository {
    suspend fun loginWithKakao(accessToken: String) : Result<KakaoLogin>
}
