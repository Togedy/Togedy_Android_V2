package com.together.study.chatbot

import com.together.study.chatbot.model.ChatMessage

data class ChatBotUiState(
    val inputText: String = "",
    val isChatMode: Boolean = false,
    val messages: List<ChatMessage> = emptyList(),
    val isFollowUpRequired: Boolean = false
)
