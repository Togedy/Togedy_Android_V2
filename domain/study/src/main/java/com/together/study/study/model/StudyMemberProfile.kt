package com.together.study.study.model

data class StudyMemberProfile(
    val userName: String,
    val studying: Boolean,
    val userProfileImageUrl: String?,
    val userProfileMessage: String,
    val totalStudyTime: String,
    val attendanceStreak: Int,
    val elapsedDays: Int,
)
