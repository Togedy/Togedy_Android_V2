package com.together.study.study.model

data class StudyDetailInfo(
    val isJoined: Boolean,
    val isStudyLeader: Boolean,
    val studyName: String,
    val studyDescription: String?,
    val studyImageUrl: String,
    val studyType: String,
    val studyTag: String,
    val studyTier: String,
    val studyMemberCount: Int,
    val completedMemberCount: Int?,
    val studyMemberLimit: Int,
    val studyPassword: String?,
)
