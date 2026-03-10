package com.together.study.mypage.ui.account

import androidx.lifecycle.ViewModel
import com.together.study.common.state.UiState
import com.together.study.mypage.state.Profile
import com.together.study.mypage.state.ProfileEditUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileEditUiState())
    val uiState: StateFlow<ProfileEditUiState> = _uiState.asStateFlow()

    fun setProfile(profile: Profile) { //서버 연결예정
        _uiState.update {
            it.copy(
                profileState = UiState.Success(profile),
                name = profile.originName,
                image = profile.originImage
            )
        }
    }

    fun updateUserName(name: String) {
        _uiState.update { it.copy(name = name) }
        if (name.length !in 2..10) setError(true, "2~10글자로 입력해주세요")
        else setError(false)
        updateDoneEnabled()
    }

    fun updateUserProfileImageUrl(url: String?) {
        _uiState.update { it.copy(image = url) }
        updateDoneEnabled()
    }

    fun setError(isError: Boolean, message: String = "") {
        _uiState.update { it.copy(isError = isError, errorMessage = message) }
    }

    fun setDupCheck(isDupCheck: Boolean) {
        _uiState.update { it.copy(isDupCheck = isDupCheck) }
        updateDoneEnabled()
    }

    fun setEditBottomSheetVisible() {
        _uiState.update { it.copy(isEditBottomSheetVisible = !uiState.value.isEditBottomSheetVisible) }
    }

    private fun checkDoneAvailable() {
        if (_uiState.value.isNameChanged) {
            _uiState.update { it.copy(isDoneEnabled = true) }
        } else {
            _uiState.update { it.copy(isDoneEnabled = false) }
        }
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