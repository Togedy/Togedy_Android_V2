package com.together.study.study.model

data class UserStudyInfo(
    val studyName: String,
    val studyImageUrl: String?,
    val studyMemberCount: Int,
    val completedMemberCount: Int?,
)
