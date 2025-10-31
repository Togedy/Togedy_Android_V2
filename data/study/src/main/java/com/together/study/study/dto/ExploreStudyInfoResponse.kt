package com.together.study.study.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExploreStudyInfoResponse(
    @SerialName("hasNext") val hasNext: Boolean,
    @SerialName("studyList") val studyList: List<ExploreStudyItemResponse>,
)

@Serializable
data class ExploreStudyItemResponse(
    @SerialName("studyId") val studyId: Long,
    @SerialName("studyType") val studyType: String,
    @SerialName("studyName") val studyName: String,
    @SerialName("studyDescription") val studyDescription: String?,
    @SerialName("studyTag") val studyTag: String,
    @SerialName("studyLeaderImageUrl") val studyLeaderImageUrl: String?,
    @SerialName("studyTier") val studyTier: String,
    @SerialName("studyMemberCount") val studyMemberCount: Int,
    @SerialName("studyMemberLimit") val studyMemberLimit: Int,
    @SerialName("studyImageUrl") val studyImageUrl: String?,
    @SerialName("isNewlyCreated") val isNewlyCreated: Boolean,
    @SerialName("lastActivatedAt") val lastActivatedAt: String?,
    @SerialName("hasPassword") val hasPassword: Boolean,
    @SerialName("challengeGoalTime") val challengeGoalTime: String?,
)
