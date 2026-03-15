package com.together.study.user.model

data class UserProfileSettings(
    val nickname: String? = null,
    val userProfileImage: String? = null,
    val removeUserProfileImage: Boolean,
)
