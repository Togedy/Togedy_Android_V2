package com.together.study.mypage.dto

import kotlinx.serialization.Serializable

@Serializable
data class PrivacyPolicyResponse(
    val privacyPolicy: String,
)
