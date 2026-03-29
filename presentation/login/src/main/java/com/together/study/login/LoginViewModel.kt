package com.together.study.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.auth.usecase.PostLoginKakaoUseCase
import com.together.study.login.state.LoginUiEvent
import com.together.study.login.state.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val postLoginKakaoUseCase: PostLoginKakaoUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<LoginUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun postLoginKakao(token: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            postLoginKakaoUseCase(token)
                .onSuccess { response ->
                    val isProfileCompleted = response.profileCompleted
                    if (isProfileCompleted) {
                        _uiEvent.emit(LoginUiEvent.NavigateToCalendar)
                    } else {
                        // TODO: 온보딩 화면 추가 후 온보딩 화면으로 이동
                        _uiEvent.emit(LoginUiEvent.NavigateToCalendar)
                    }
                }
                .onFailure { e ->
                    _uiEvent.emit(LoginUiEvent.ShowError(e.message ?: "로그인에 실패했습니다"))
                }

            _uiState.value = LoginUiState.Idle
        }
    }
}