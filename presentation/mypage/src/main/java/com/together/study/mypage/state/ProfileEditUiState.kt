package com.together.study.mypage.state

import com.together.study.common.state.UiState

data class ProfileEditUiState(
    val profileState: UiState<Profile> = UiState.Loading,
    val name: String = "",
    val image: String? = "",
    val isError: Boolean = false,
    val errorMessage: String = "",
    val isDupCheck: Boolean = false,
    val isNameChanged: Boolean = false,
    val isEditBottomSheetVisible: Boolean = false,
    val isDoneEnabled: Boolean = false,
)

data class Profile(
    val originName: String = "",
    val originImage: String? = "",
)
