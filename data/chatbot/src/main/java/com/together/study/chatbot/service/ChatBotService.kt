package com.together.study.chatbot.service

import com.together.study.chatbot.dto.ChatBotRequest
import com.together.study.chatbot.dto.ChatBotResponse
import com.together.study.remote.model.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatBotService {
    @POST("chatbots/questions")
    suspend fun postQuestion(
        @Body request: ChatBotRequest
    ): BaseResponse<ChatBotResponse>
}