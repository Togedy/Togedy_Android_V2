package com.together.study.chatbot.datasource

import com.together.study.chatbot.dto.ChatBotRequest
import com.together.study.chatbot.service.ChatBotService
import javax.inject.Inject

class ChatBotDataSource @Inject constructor(
    private val chatBotService: ChatBotService,
) {
    suspend fun postQuestion(question: String, followUpAnswer: String?) =
        chatBotService.postQuestion(
            request = ChatBotRequest(
                question = question,
                followUpAnswer = followUpAnswer,
            )
        )
}