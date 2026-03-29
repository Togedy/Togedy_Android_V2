package com.together.study.auth.mapper

import com.together.study.auth.dto.KakaoLoginResponse
import com.together.study.auth.model.KakaoLogin

fun KakaoLoginResponse.toDomain(): KakaoLogin {
    return KakaoLogin(
        accessToken = this.jwtTokenInfo.accessToken.removeBearerPrefix(),
        refreshToken = this.jwtTokenInfo.refreshToken.removeBearerPrefix(),
        profileCompleted = profileCompleted,
    )
}

private fun String.removeBearerPrefix(): String = removePrefix("Bearer ").trim()