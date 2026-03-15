package com.together.study.user.model

data class UserInfo(
    val userName: String,
    val userEmail: String,
    val userProfileImageUrl: String?,
    val totalStudyTime: String,
    val attendanceStreak: Int,
    val studies: List<UserStudyInfo>,
)
