package com.together.study.chatbot

import androidx.lifecycle.ViewModel
import com.together.study.chatbot.model.ChatMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
internal class ChatBotViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatBotUiState())
    val uiState: StateFlow<ChatBotUiState> = _uiState.asStateFlow()

    fun updateInputText(text: String) {
        _uiState.update { it.copy(inputText = text) }
    }

    fun sendMessage(message: String) {
        if (message.isBlank()) return

        val userMessage = ChatMessage(message = message, isMine = true)
        _uiState.update {
            it.copy(
                messages = it.messages + userMessage,
                inputText = "",
                isChatMode = true,
            )
        }

        // TODO: 챗봇 응답 로직 추가 (API 연동 시)
        val botResponse = ChatMessage(
            message = "안녕하세요! 질문을 확인했습니다. 답변을 준비 중이에요.",
            isMine = false,
        )
        _uiState.update {
            it.copy(messages = it.messages + botResponse)
        }
    }
}
