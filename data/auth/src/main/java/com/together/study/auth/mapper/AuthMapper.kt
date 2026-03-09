package com.together.study.auth.mapper

import com.together.study.auth.dto.KakaoLoginResponse
import com.together.study.auth.model.KakaoLogin

fun KakaoLoginResponse.toDomain(): KakaoLogin {
    return KakaoLogin(
        accessToken = this.jwtTokenInfo.accessToken,
        refreshToken = this.jwtTokenInfo.refreshToken,
        profileCompleted = profileCompleted,
    )
}
