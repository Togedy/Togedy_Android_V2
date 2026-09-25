package com.together.study.login.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.auth.usecase.CheckTokenUseCase
import com.together.study.study.deeplink.PendingStudyDeepLinkStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SplashUiEvent {
    data object NavigateToLogin : SplashUiEvent
    data object NavigateToCalendar : SplashUiEvent
    data class NavigateToStudy(val studyId: Long) : SplashUiEvent
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkTokenUseCase: CheckTokenUseCase,
    private val pendingStudyDeepLinkStore: PendingStudyDeepLinkStore,
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<SplashUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        checkAuthState()
    }

    private fun checkAuthState() {
        viewModelScope.launch {
            val hasValidToken = checkTokenUseCase()
            val pendingStudyId = if (hasValidToken) pendingStudyDeepLinkStore.consume() else null
            when {
                pendingStudyId != null -> _uiEvent.emit(SplashUiEvent.NavigateToStudy(pendingStudyId))
                hasValidToken -> _uiEvent.emit(SplashUiEvent.NavigateToCalendar)
                else -> _uiEvent.emit(SplashUiEvent.NavigateToLogin)
            }
        }
    }
}
