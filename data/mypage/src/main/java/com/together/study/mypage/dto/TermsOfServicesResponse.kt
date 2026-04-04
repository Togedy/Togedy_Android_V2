package com.together.study.mypage.dto

import kotlinx.serialization.Serializable

@Serializable
data class TermsOfServicesResponse(
    val termsOfService: String,
)
