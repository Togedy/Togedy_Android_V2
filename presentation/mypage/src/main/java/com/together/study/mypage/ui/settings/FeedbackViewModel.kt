package com.together.study.mypage.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.mypage.event.FeedbackEvent
import com.together.study.mypage.state.FeedbackUiState
import com.together.study.mypage.type.FeedbackType
import com.together.study.mypage.usecase.PostContactUsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedbackViewModel @Inject constructor(
    private val postContactUsUseCase: PostContactUsUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(FeedbackUiState())
    val uiState = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<FeedbackEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun updateType(type: FeedbackType) {
        _uiState.value = _uiState.value.copy(type = type)
        checkDoneEnabled()
    }

    fun updateContent(content: String) {
        _uiState.value = _uiState.value.copy(content = content)
        checkDoneEnabled()
    }

    fun updateEmail(title: String) {
        _uiState.value = _uiState.value.copy(replyEmail = title)
        checkDoneEnabled()
    }

    fun checkDoneEnabled() {
        val currentState = _uiState.value
        _uiState.value = currentState.copy(
            isDoneEnabled = currentState.type != null && currentState.content.isNotBlank() && currentState.replyEmail.isNotBlank()
        )
    }

    fun postFeedback() = viewModelScope.launch {
        val currentState = _uiState.value
        if (currentState.type == null) {
            _eventFlow.emit(FeedbackEvent.PostFeedbackFailure("문의 유형을 선택해주세요"))
        } else if (currentState.content.trim().isEmpty()) {
            _eventFlow.emit(FeedbackEvent.PostFeedbackFailure("문의 내용을 입력해주세요"))
        } else if (currentState.replyEmail.trim().isEmpty()) {
            _eventFlow.emit(FeedbackEvent.PostFeedbackFailure("답변 받을 이메일을 입력해주세요"))
        } else {
            postContactUsUseCase(
                inquiryType = currentState.type.serverValue,
                inquiryContent = currentState.content,
                replyEmail = currentState.replyEmail,
            )
                .onSuccess {
                    _eventFlow.emit(FeedbackEvent.PostFeedbackSuccess)
                }
                .onFailure {
                    _eventFlow.emit(FeedbackEvent.PostFeedbackFailure("문의 제출에 실패했습니다"))
                }
        }
    }
}