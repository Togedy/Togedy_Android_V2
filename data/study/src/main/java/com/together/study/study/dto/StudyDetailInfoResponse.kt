package com.together.study.study.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudyDetailInfoResponse(
    @SerialName("isJoined") val isJoined: Boolean,
    @SerialName("isStudyLeader") val isStudyLeader: Boolean,
    @SerialName("studyName") val studyName: String,
    @SerialName("studyDescription") val studyDescription: String?,
    @SerialName("studyImageUrl") val studyImageUrl: String,
//    @SerialName("studyType") val studyType: String,
    @SerialName("studyTag") val studyTag: String,
    @SerialName("studyTier") val studyTier: String,
    @SerialName("studyMemberCount") val studyMemberCount: Int,
    @SerialName("completedMemberCount") val completedMemberCount: Int?,
    @SerialName("studyMemberLimit") val studyMemberLimit: Int,
    @SerialName("studyPassword") val studyPassword: String?,
)
