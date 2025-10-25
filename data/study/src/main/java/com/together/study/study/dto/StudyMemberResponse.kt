package com.together.study.study.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudyMemberResponse(
    @SerialName("userId") val userId: Long,
    @SerialName("userName") val userName: String,
    @SerialName("userProfileImageUrl") val userProfileImageUrl: String?,
    @SerialName("studyRole") val studyRole: String,
    @SerialName("userStatus") val userStatus: String,
    @SerialName("studyTime") val studyTime: String?,
    @SerialName("lastActivatedAt") val lastActivatedAt: String?,
)
