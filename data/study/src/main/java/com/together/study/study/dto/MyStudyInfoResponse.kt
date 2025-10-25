package com.together.study.study.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyStudyInfoResponse(
    @SerialName("hasChallenge") val hasChallenge: Boolean,
    @SerialName("goalTime") val goalTime: String?,
    @SerialName("studyTime") val studyTime: String?,
    @SerialName("achievement") val achievement: Int?,
    @SerialName("studyList") val studyList: List<MyStudyItemResponse>,
)

@Serializable
data class MyStudyItemResponse(
    @SerialName("studyId") val studyId: Long,
    @SerialName("studyType") val studyType: String,
    @SerialName("challengeGoalTime") val challengeGoalTime: String?,
    @SerialName("challengeAchievement") val challengeAchievement: Int?,
    @SerialName("studyName") val studyName: String,
    @SerialName("completedMemberCount") val completedMemberCount: Int?,
    @SerialName("studyMemberCount") val studyMemberCount: Int,
    @SerialName("activeMemberList") val activeMemberList: List<ActiveMemberResponse>,
)

@Serializable
data class ActiveMemberResponse(
    @SerialName("userName") val userName: String,
    @SerialName("userProfileImageUrl") val userProfileImageUrl: String,
)
