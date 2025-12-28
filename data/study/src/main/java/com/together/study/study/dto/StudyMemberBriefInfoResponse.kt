package com.together.study.study.dto

import com.together.study.study.type.StudyRole
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudyMemberBriefInfoResponse(
    @SerialName("userId") val userId: Long,
    @SerialName("userName") val userName: String,
    @SerialName("userProfileImageUrl") val userProfileImageUrl: String?,
    @SerialName("studyRole") val studyRole: StudyRole,
)
