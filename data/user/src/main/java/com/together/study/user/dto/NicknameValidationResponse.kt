package com.together.study.user.dto

import kotlinx.serialization.Serializable

@Serializable
data class NicknameValidationResponse(
    val available: Boolean,
    val reason: String,
    val message: String,
)
