package com.together.study.mypage.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.common.state.UiState
import com.together.study.mypage.model.Notice
import com.together.study.mypage.state.MyPageUiState
import com.together.study.mypage.usecase.GetNoticesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoticeMainViewModel @Inject constructor(
    private val getNoticesUseCase: GetNoticesUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Notice>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Notice>>> = _uiState.asStateFlow()

    fun getNoticeList() = viewModelScope.launch {
        getNoticesUseCase()
            .onSuccess {  _uiState.value = UiState.Success(it) }
            .onFailure { _uiState.value = UiState.Failure(it.toString()) }
    }
}
