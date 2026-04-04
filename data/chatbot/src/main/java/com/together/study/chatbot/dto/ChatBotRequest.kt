package com.together.study.chatbot.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatBotRequest(
    @SerialName("question") val question: String,
    @SerialName("followUpAnswer") val followUpAnswer: String? = null,
)