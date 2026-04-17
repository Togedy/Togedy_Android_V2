package com.together.study.onboarding.state

data class OnboardingUiState(
    val nickname: String = "",
    val isNicknameValidated: Boolean = false,
    val nicknameErrorMessage: String? = null,
    val isValidatingNickname: Boolean = false,
    val isSubmitting: Boolean = false,
)

sealed interface OnboardingUiEvent {
    data object NavigateToCalendar : OnboardingUiEvent
    data class ShowError(val message: String) : OnboardingUiEvent
}
