package com.together.study.studydetail.detailmain

import com.together.study.common.type.study.StudyRole
import com.together.study.common.type.study.UserStatus

// TODO : domain으로 이동 후 삭제
data class StudyMember(
    val userId: Long,
    val userName: String,
    val userProfileImageUrl: String,
    val studyRole: StudyRole,
    val userStatus: UserStatus,
    val totalStudyAmount: String?,
    val lastActivatedAt: String?,
) {
    companion object {
        val mock_leader = StudyMember(
            userId = 1,
            userName = "내가리더임",
            studyRole = StudyRole.LEADER,
            userStatus = UserStatus.ACTIVE,
            userProfileImageUrl = "",
            totalStudyAmount = "1H 20M",
            lastActivatedAt = null,
        )
        val mock_member = StudyMember(
            userId = 1,
            userName = "투게디공부핑1투게디공부핑1투게디공부핑1",
            studyRole = StudyRole.MEMBER,
            userStatus = UserStatus.STUDYING,
            userProfileImageUrl = "",
            totalStudyAmount = null,
            lastActivatedAt = "3분 전",
        )
        val mock_member2 = StudyMember(
            userId = 1,
            userName = "투게디공부핑2",
            studyRole = StudyRole.MEMBER,
            userStatus = UserStatus.ACTIVE,
            userProfileImageUrl = "",
            totalStudyAmount = null,
            lastActivatedAt = "3분 전",
        )
        val mock_member3 = StudyMember(
            userId = 1,
            userName = "투게디공부핑3",
            studyRole = StudyRole.MEMBER,
            userStatus = UserStatus.ACTIVE,
            userProfileImageUrl = "",
            totalStudyAmount = null,
            lastActivatedAt = null,
        )
    }
}
