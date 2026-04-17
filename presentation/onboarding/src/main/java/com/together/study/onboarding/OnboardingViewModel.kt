package com.together.study.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.onboarding.state.OnboardingUiEvent
import com.together.study.onboarding.state.OnboardingUiState
import com.together.study.user.usecase.CompleteOnboardingUseCase
import com.together.study.user.usecase.GetNicknameSuggestionUseCase
import com.together.study.user.usecase.ValidateNicknameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val getNicknameSuggestionUseCase: GetNicknameSuggestionUseCase,
    private val validateNicknameUseCase: ValidateNicknameUseCase,
    private val completeOnboardingUseCase: CompleteOnboardingUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<OnboardingUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        fetchNicknameSuggestion()
    }

    private fun fetchNicknameSuggestion() {
        viewModelScope.launch {
            getNicknameSuggestionUseCase()
                .onSuccess { nickname ->
                    _uiState.update {
                        it.copy(nickname = nickname)
                    }
                }
        }
    }

    fun updateNickname(nickname: String) {
        _uiState.update {
            it.copy(
                nickname = nickname.take(10),
                isNicknameValidated = false,
                nicknameErrorMessage = null,
            )
        }
    }

    fun validateNickname() {
        val nickname = _uiState.value.nickname

        if (nickname.isBlank()) {
            _uiState.update { it.copy(nicknameErrorMessage = "닉네임을 입력해주세요") }
            return
        }
        if (nickname.length !in 2..10) {
            _uiState.update { it.copy(nicknameErrorMessage = "2~10글자로 입력해주세요") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isValidatingNickname = true) }

            validateNicknameUseCase(nickname)
                .onSuccess { result ->
                    if (result.available) {
                        _uiState.update {
                            it.copy(
                                isNicknameValidated = true,
                                nicknameErrorMessage = null,
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                isNicknameValidated = false,
                                nicknameErrorMessage = result.message,
                            )
                        }
                    }
                }
                .onFailure { e ->
                    _uiState.update {
                        it.copy(
                            nicknameErrorMessage = e.message ?: "닉네임 검증에 실패했습니다",
                        )
                    }
                }

            _uiState.update { it.copy(isValidatingNickname = false) }
        }
    }

    fun completeOnboarding(birthDate: LocalDate) {
        val nickname = _uiState.value.nickname
        val birthDateString = birthDate.format(DateTimeFormatter.ISO_LOCAL_DATE)

        viewModelScope.launch {
            _uiState.update { it.copy(isSubmitting = true) }

            completeOnboardingUseCase(nickname, birthDateString)
                .onSuccess {
                    _uiEvent.emit(OnboardingUiEvent.NavigateToCalendar)
                }
                .onFailure { e ->
                    _uiEvent.emit(
                        OnboardingUiEvent.ShowError(e.message ?: "온보딩 완료에 실패했습니다")
                    )
                }

            _uiState.update { it.copy(isSubmitting = false) }
        }
    }
}
