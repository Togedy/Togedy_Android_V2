package com.together.study.chatbot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.chatbot.model.ChatMessage
import com.together.study.chatbot.repository.ChatBotRepository
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
    private val chatBotRepository: ChatBotRepository,
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

            // 실제 API 호출
            chatBotRepository.postQuestion(
                question = message,
                followUpAnswer = null,
            ).onSuccess { answer ->
                val fullResponse = answer.answer

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
                        },
                        isFollowUpRequired = answer.isFollowUpRequired,
                    )
                }

                messageResponseAnimation(typingMessage.id, fullResponse)
            }.onFailure { error ->
                // 에러 발생 시 에러 메시지로 교체
                val errorMessage = ChatMessage(
                    id = loadingMessage.id,
                    message = "죄송합니다. 응답을 가져오는데 실패했습니다. 다시 시도해주세요.",
                    isMine = false,
                    isLoading = false,
                    displayedText = "죄송합니다. 응답을 가져오는데 실패했습니다. 다시 시도해주세요.",
                    isAnimating = false,
                )
                _uiState.update { state ->
                    state.copy(
                        messages = state.messages.map {
                            if (it.id == loadingMessage.id) errorMessage else it
                        }
                    )
                }
            }
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
