package com.together.study.study.model

import com.together.study.study.type.StudyRole
import com.together.study.study.type.UserStatus

data class StudyMember(
    val userId: Long,
    val userName: String,
    val userProfileImageUrl: String,
    val studyRole: StudyRole,
    val userStatus: UserStatus,
    val totalStudyAmount: String?,
    val lastActivatedAt: String?,
)