package com.together.study.study.model

data class ExploreStudyItem(
    val studyId: Long,
    val studyType: String,
    val studyName: String,
    val studyDescription: String?,
    val studyTag: String,
    val studyLeaderImageUrl: String,
    val studyTier: String?,
    val studyMemberCount: Int,
    val studyMemberLimit: Int,
    val studyImageUrl: String,
    val isNewlyCreated: Boolean,
    val lastActivatedAt: String?,
    val hasPassword: Boolean,
    val challengeGoalTime: String?,
)
