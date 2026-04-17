package com.together.study.login.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.auth.usecase.CheckTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SplashUiEvent {
    data object NavigateToLogin : SplashUiEvent
    data object NavigateToCalendar : SplashUiEvent
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkTokenUseCase: CheckTokenUseCase,
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<SplashUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        checkAuthState()
    }

    private fun checkAuthState() {
        viewModelScope.launch {
            val hasValidToken = checkTokenUseCase()
            if (hasValidToken) {
                _uiEvent.emit(SplashUiEvent.NavigateToCalendar)
            } else {
                _uiEvent.emit(SplashUiEvent.NavigateToLogin)
            }
        }
    }
}
