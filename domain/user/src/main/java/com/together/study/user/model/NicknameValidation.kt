package com.together.study.user.model

data class NicknameValidation(
    val available: Boolean,
    val reason: String,
    val message: String,
)
