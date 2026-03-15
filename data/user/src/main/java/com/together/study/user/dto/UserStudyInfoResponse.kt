package com.together.study.user.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserStudyInfoResponse(
    val studyId: Long,
    val studyName: String,
    val studyImageUrl: String?,
    val isCompleted: Boolean?,
    val completedMemberCount: Int?,
    val studyMemberCount: Int?,
)
