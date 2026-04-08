package com.together.study.chatbot.repositoryimpl

import com.together.study.chatbot.datasource.ChatBotDataSource
import com.together.study.chatbot.dto.ChatBotRequest
import com.together.study.chatbot.mapper.toDomain
import com.together.study.chatbot.model.ChatBotAnswer
import com.together.study.chatbot.repository.ChatBotRepository
import javax.inject.Inject

class ChatBotRepositoryImpl @Inject constructor(
    private val chatBotDataSource: ChatBotDataSource,
) : ChatBotRepository {
    override suspend fun postQuestion(
        question: String,
        followUpAnswer: String?,
    ): Result<ChatBotAnswer> = runCatching {
        val response = chatBotDataSource.postQuestion(question, followUpAnswer).response
        response.toDomain()
    }
}