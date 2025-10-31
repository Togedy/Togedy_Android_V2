package com.together.study.study.model

data class StudyMemberProfile(
    val userName: String,
    val userStatus: String,
    val userProfileImageUrl: String,
    val userProfileMessage: String,
    val totalStudyTime: String,
    val attendanceStreak: Int,
    val elapsedDays: Int,
)
