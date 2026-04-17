package com.together.study.user.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OnboardingRequest(
    @SerialName("nickname")
    val nickname: String,
    @SerialName("birthDate")
    val birthDate: String,
)
