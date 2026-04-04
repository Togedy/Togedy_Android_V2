package com.together.study.user.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponse(
    val userName: String,
    val userEmail: String,
    val userProfileImageUrl: String?,
    val totalStudyTime: String,
    val attendanceStreak: Int,
    val studies: List<UserStudyInfoResponse>,
)
