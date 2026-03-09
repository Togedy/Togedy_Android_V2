package com.together.study.auth.model

data class KakaoLogin(
    val accessToken: String,
    val refreshToken: String,
    val profileCompleted: Boolean,
)
