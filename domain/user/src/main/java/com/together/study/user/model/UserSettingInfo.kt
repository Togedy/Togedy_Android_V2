package com.together.study.user.model

data class UserSettingInfo(
    val pushNotificationEnabled: Boolean = false,
    val marketingConsented: Boolean = false,
    val userEmail: String,
)
