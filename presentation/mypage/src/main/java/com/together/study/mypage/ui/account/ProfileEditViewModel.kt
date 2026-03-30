package com.together.study.mypage.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.common.state.UiState
import com.together.study.mypage.state.Profile
import com.together.study.mypage.state.ProfileEditUiState
import com.together.study.user.usecase.GetUserInfoUseCase
import com.together.study.user.usecase.UpdateUserInfoUseCase
import com.together.study.user.usecase.ValidateNicknameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val validateNicknameUseCase: ValidateNicknameUseCase,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileEditUiState())
    val uiState: StateFlow<ProfileEditUiState> = _uiState.asStateFlow()

    private var tempName = ""

    init {
        setProfile()
    }

    fun setProfile() = viewModelScope.launch {
        getUserInfoUseCase()
            .onSuccess { result ->
                _uiState.update {
                    it.copy(
                        profileState = UiState.Success(
                            Profile(
                                originName = result.userName,
                                originImage = result.userProfileImageUrl,
                            )
                        ),
                        name = result.userName,
                        image = result.userProfileImageUrl,
                    )
                }
            }
            .onFailure {
                _uiState.update { it.copy(profileState = UiState.Failure(it.toString())) }
            }
    }

    fun checkDuplication() = viewModelScope.launch {
        validateNicknameUseCase(uiState.value.name)
            .onSuccess { result ->
                if (result.available) {
                    setDupCheck(true)
                    tempName = uiState.value.name
                    setError(false, "")
                    updateDoneEnabled()
                } else {
                    setDupCheck(false)
                    setError(true, result.message)
                }
            }
            .onFailure {
                setError(true, "서버 오류로 중복확인에 실패했습니다.")
            }
    }

    fun updateProfile() = viewModelScope.launch {
        val removeImg = uiState.value.image == null

        updateUserInfoUseCase(
            userName = uiState.value.name,
            userProfileImage = uiState.value.image,
            removeUserProfileImage = removeImg,
        )
            .onSuccess {
                // toast & 뒤로가기
            }
            .onFailure {

            }
    }

    fun updateUserName(name: String) {
        _uiState.update { it.copy(name = name) }

        if (name != tempName) setDupCheck(false)
        else setDupCheck(true)
    }

    fun updateUserProfileImageUrl(url: String?) {
        _uiState.update { it.copy(image = url) }
        updateDoneEnabled()
    }

    fun setDupCheck(isDupCheck: Boolean) {
        _uiState.update { it.copy(isDupCheck = isDupCheck) }
        updateDoneEnabled()
    }

    fun setEditBottomSheetVisible() {
        _uiState.update { it.copy(isEditBottomSheetVisible = !uiState.value.isEditBottomSheetVisible) }
    }

    fun setError(isError: Boolean, message: String = "") {
        _uiState.update { it.copy(isError = isError, errorMessage = message) }
    }

    private fun updateDoneEnabled() {
        val state = _uiState.value
        val originName = (state.profileState as? UiState.Success)?.data?.originName ?: ""
        val originImage = (state.profileState as? UiState.Success)?.data?.originImage

        val isNameValid =
            state.name.length in 2..10 &&
                    state.isDupCheck &&
                    state.name != originName

        val isImageChanged = state.image != originImage
        val doneEnabled = isNameValid || isImageChanged

        _uiState.update { it.copy(isDoneEnabled = doneEnabled) }
    }
}
