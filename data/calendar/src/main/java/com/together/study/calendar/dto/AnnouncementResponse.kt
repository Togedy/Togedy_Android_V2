package com.together.study.calendar.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnnouncementResponse(
    @SerialName("hasAnnouncement") val hasAnnouncement: Boolean,
    @SerialName("announcement") val announcement: String,
)
