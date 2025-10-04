package com.together.study.studydetail.detailmain

import com.together.study.common.type.study.StudyRole
import com.together.study.common.type.study.UserStatus

// TODO : domain으로 이동 후 삭제
data class StudyMember(
    val userId: Int,
    val userName: String,
    val studyRole: StudyRole,
    val userStatus: UserStatus,
    val userProfileImageUrl: String,
    val lastActivatedAt: String?,
)
