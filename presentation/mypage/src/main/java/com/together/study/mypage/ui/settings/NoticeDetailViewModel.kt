package com.together.study.mypage.ui.settings

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.common.state.UiState
import com.together.study.mypage.model.Notice
import com.together.study.mypage.usecase.GetNoticeDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoticeDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getNoticeDetailUseCase: GetNoticeDetailUseCase,
) : ViewModel() {
    val noticeId = savedStateHandle.get<Long>("noticeId") ?: -1L

    private val _uiState = MutableStateFlow<UiState<Notice>>(UiState.Loading)
    val uiState: StateFlow<UiState<Notice>> = _uiState.asStateFlow()

    fun getNoticeDetailInfo() = viewModelScope.launch {
        getNoticeDetailUseCase(noticeId)
            .onSuccess {  _uiState.value = UiState.Success(it) }
            .onFailure { _uiState.value = UiState.Failure(it.toString()) }
    }
}
