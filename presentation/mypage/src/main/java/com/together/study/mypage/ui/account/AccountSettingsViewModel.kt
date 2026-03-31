package com.together.study.mypage.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.common.state.UiState
import com.together.study.mypage.state.AccountSettingsUiState
import com.together.study.user.usecase.GetUserSettingInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountSettingsViewModel @Inject constructor(
    private val getUserSettingInfoUseCase: GetUserSettingInfoUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AccountSettingsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadUserSettingInfo()
    }

    fun loadUserSettingInfo() = viewModelScope.launch {
        getUserSettingInfoUseCase()
            .onSuccess { result ->
                _uiState.value = AccountSettingsUiState(uiState = UiState.Success(result))
            }
            .onFailure {
                _uiState.value = AccountSettingsUiState(uiState = UiState.Failure(it.toString()))
            }
    }

    fun logout() = viewModelScope.launch {
        /* TODO API 연결 */
    }
}
