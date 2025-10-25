package com.together.study.study.dto

import com.together.study.study.type.StudyRole
import com.together.study.study.type.UserStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudyMemberResponse(
    @SerialName("userId") val userId: Long,
    @SerialName("userName") val userName: String,
    @SerialName("userProfileImageUrl") val userProfileImageUrl: String,
    @SerialName("studyRole") val studyRole: StudyRole,
    @SerialName("userStatus") val userStatus: UserStatus,
    @SerialName("totalStudyAmount") val totalStudyAmount: String?,
    @SerialName("lastActivatedAt") val lastActivatedAt: String?,
)
