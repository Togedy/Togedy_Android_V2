package com.together.study.user.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NicknameSuggestionResponse(
    @SerialName("nickname")
    val nickname: String,
)
