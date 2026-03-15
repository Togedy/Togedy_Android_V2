package com.together.study.mypage.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.common.state.UiState
import com.together.study.mypage.state.MyPageUiState
import com.together.study.user.model.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow(MyPageUiState())
    val uiState: StateFlow<MyPageUiState> = _uiState.asStateFlow()

    init {
        loadUserInfo()
    }

    private fun loadUserInfo() = viewModelScope.launch {
        _uiState.value = MyPageUiState(
            userInfoState = UiState.Success(
                UserInfo(
                    userName = "유저입니당",
                    userEmail = "user@gmail.com",
                    userProfileImageUrl = "http://~~",
                    totalStudyTime = "100:00:00",
                    attendanceStreak = 4,
                    studies = listOf(),
                )
            )
        )
    }

    companion object {
        const val TAG = "MyPageViewModel"
    }
}
