package com.together.study.mypage.event

sealed class ProfileEditEvent {
    data object UpdateProfileSuccess : ProfileEditEvent()
    data class UpdateProfileFailure(val message: String) : ProfileEditEvent()
}