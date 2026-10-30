package com.together.study.study.model

data class StudyBadge(
    val badgeId: Long,
    val badgeName: String,
    val badgeDescription: String,
    val badgeImageUrl: String?,
    val isAcquired: Boolean,
    val acquiredAt: String?,
)
