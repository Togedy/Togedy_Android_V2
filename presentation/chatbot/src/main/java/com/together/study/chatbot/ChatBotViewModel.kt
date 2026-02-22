package com.together.study.chatbot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.chatbot.model.ChatMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

        val userMessage = ChatMessage(
            message = message,
            isMine = true,
            isAnimating = true,
        )
        _uiState.update {
            it.copy(
                messages = it.messages + userMessage,
                inputText = "",
                isChatMode = true,
            )
        }

        viewModelScope.launch {
            // 300ms 딜레이 후 봇 응답 추가
            delay(300)

            // 로딩 상태로 먼저 추가
            val loadingMessage = ChatMessage(
                message = "생각중...",
                isMine = false,
                isLoading = true,
                isAnimating = true,
            )
            _uiState.update {
                it.copy(messages = it.messages + loadingMessage)
            }

            // TODO: 실제 API 호출로 대체
            // 1.5초 딜레이
            delay(1500)

            val fullResponse = "안녕하세요! 질문을 확인했습니다. 답변을 준비 중이에요. $message 대해 궁금해하시는군요." +
                    "\n\n 밥은 드시고 질문하시나요? 저는 고기를 제일 좋아해요." +
                    "\n\n 회사 다니기 싫어요. 더미데이터는 여기까지 할게요"

            // 로딩 메시지를 타이핑 애니메이션 메시지로 교체
            val typingMessage = ChatMessage(
                id = loadingMessage.id,
                message = fullResponse,
                isMine = false,
                isLoading = false,
                displayedText = "",
                isAnimating = true,
            )
            _uiState.update { state ->
                state.copy(
                    messages = state.messages.map {
                        if (it.id == loadingMessage.id) typingMessage else it
                    }
                )
            }

            messageResponseAnimation(typingMessage.id, fullResponse)
        }
    }

    private suspend fun messageResponseAnimation(messageId: String, fullText: String) {
        for (i in 1..fullText.length) {
            delay(30) // 글자당 30ms
            _uiState.update { state ->
                state.copy(
                    messages = state.messages.map { msg ->
                        if (msg.id == messageId) {
                            msg.copy(
                                displayedText = fullText.take(i),
                                isAnimating = i < fullText.length,
                            )
                        } else msg
                    }
                )
            }
        }
    }
}
