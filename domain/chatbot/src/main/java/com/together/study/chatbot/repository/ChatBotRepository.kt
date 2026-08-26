package com.together.study.chatbot.repository

import com.together.study.chatbot.model.ChatBotAnswer

interface ChatBotRepository {
    suspend fun postQuestion(
        question: String,
        followUpAnswer: String? = null,
    ): Result<ChatBotAnswer>

    suspend fun getTodayQuestionCount(): Int

    suspend fun incrementTodayQuestionCount()
}