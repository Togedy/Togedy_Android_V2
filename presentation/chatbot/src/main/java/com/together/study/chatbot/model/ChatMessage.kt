package com.together.study.chatbot.model

data class ChatMessage(
    val message: String,
    val isMine: Boolean,
    val isLoading: Boolean = false,
)
