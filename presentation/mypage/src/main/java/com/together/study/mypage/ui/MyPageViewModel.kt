package com.together.study.mypage.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.common.state.UiState
import com.together.study.mypage.state.MyPageUiState
import com.together.study.user.usecase.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyPageUiState())
    val uiState: StateFlow<MyPageUiState> = _uiState.asStateFlow()

    fun loadUserInfo() = viewModelScope.launch {
        _uiState.update { it.copy(userInfoState = UiState.Loading) }

        getUserInfoUseCase()
            .onSuccess { result ->
                _uiState.value = MyPageUiState(userInfoState = UiState.Success(result))
            }
            .onFailure {
                _uiState.value = MyPageUiState(userInfoState = UiState.Failure(it.toString()))
                Timber.tag("okhttp-mypage").d("loadUserInfo: ${it.message}")
            }
    }
}
