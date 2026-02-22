package com.together.study.chatbot.model

import java.util.UUID

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val message: String,
    val isMine: Boolean,
    val isLoading: Boolean = false,
    val displayedText: String = message,
    val isAnimating: Boolean = false,
)
