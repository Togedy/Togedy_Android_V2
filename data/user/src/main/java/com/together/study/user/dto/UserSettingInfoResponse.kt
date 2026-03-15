package com.together.study.user.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserSettingInfoResponse(
    val pushNotificationEnabled: Boolean = false,
    val marketingConsented: Boolean = false,
    val userEmail: String,
)
