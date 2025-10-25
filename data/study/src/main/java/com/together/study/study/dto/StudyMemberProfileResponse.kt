package com.together.study.study.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudyMemberProfileResponse(
    @SerialName("userName") val userName: String,
    @SerialName("userStatus") val userStatus: String,
    @SerialName("userProfileImageUrl") val userProfileImageUrl: String,
    @SerialName("userProfileMessage") val userProfileMessage: String?,
    @SerialName("totalStudyTime") val totalStudyTime: String,
    @SerialName("attendanceStreak") val attendanceStreak: Int,
)
