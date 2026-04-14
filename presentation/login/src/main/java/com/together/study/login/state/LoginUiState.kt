package com.together.study.login.state

sealed interface LoginUiState {
    data object Idle : LoginUiState
    data object Loading : LoginUiState
}

sealed interface LoginUiEvent {
    data object NavigateToCalendar : LoginUiEvent
    data object NavigateToOnboarding : LoginUiEvent
    data class ShowError(val message: String) : LoginUiEvent
}
