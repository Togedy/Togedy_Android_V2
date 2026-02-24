package com.together.study.chatbot.model

data class ChatBotAnswer(
    val answer: String,
    val isFollowUpRequired: Boolean,
)