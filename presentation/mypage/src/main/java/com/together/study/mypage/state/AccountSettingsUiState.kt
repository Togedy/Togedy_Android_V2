package com.together.study.mypage.state

import com.together.study.common.state.UiState
import com.together.study.user.model.UserSettingInfo

data class AccountSettingsUiState(
    val uiState: UiState<UserSettingInfo> = UiState.Loading,
)
