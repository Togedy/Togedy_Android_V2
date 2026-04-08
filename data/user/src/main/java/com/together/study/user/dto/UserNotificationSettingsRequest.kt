package com.together.study.user.dto

import kotlinx.serialization.Serializable

@Serializable
data class NotificationSettingsRequest(
    val pushNotificationEnabled: Boolean,
)
