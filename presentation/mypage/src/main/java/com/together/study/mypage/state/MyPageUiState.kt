package com.together.study.mypage.state

import com.together.study.common.state.UiState
import com.together.study.user.model.UserInfo

data class MyPageUiState(
    val userInfoState: UiState<UserInfo> = UiState.Loading,
)
