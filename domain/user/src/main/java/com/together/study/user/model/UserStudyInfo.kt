package com.together.study.user.model

data class UserStudyInfo(
    val studyName: String,
    val studyImageUrl: String?,
    val isCompleted: Boolean?,
    val completedMemberCount: Int?,
    val studyMemberCount: Int,
)
