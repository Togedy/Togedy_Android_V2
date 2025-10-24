package com.together.study.study.model

data class MyStudyItem(
    val studyId: Long,
    val studyType: String,
    val challengeGoalTime: String?,
    val challengeAchievement: Int?,
    val studyName: String,
    val completedMemberCount: Int?,
    val studyMemberCount: Int,
    val activeMemberList: List<ActiveMember> = emptyList(),
) {
    companion object {
        data class ActiveMember(
            val userName: String,
            val userProfileImageUrl: String,
        )
    }
}
