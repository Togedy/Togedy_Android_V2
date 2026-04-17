package com.together.study.auth.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KakaoLoginResponse(
    @SerialName("jwtTokenInfo")
    val jwtTokenInfo: JwtTokenInfo,
    @SerialName("profileCompleted")
    val profileCompleted: Boolean,
)

@Serializable
data class JwtTokenInfo(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String,
)
