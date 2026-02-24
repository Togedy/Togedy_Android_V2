package com.together.study.chatbot.mapper

import com.together.study.chatbot.dto.ChatBotResponse
import com.together.study.chatbot.model.ChatBotAnswer

fun ChatBotResponse.toDomain(): ChatBotAnswer =
    ChatBotAnswer(
        answer = answer,
        isFollowUpRequired = isFollowUpRequired,
    )