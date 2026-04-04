package com.together.study.chatbot.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatBotResponse(
    @SerialName("answer") val answer: String,
    @SerialName("isFollowUpRequired") val isFollowUpRequired: Boolean,
)