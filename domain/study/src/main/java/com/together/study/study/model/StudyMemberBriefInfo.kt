package com.together.study.study.model

import com.together.study.study.type.StudyRole

data class StudyMemberBriefInfo(
    val userId: Long,
    val userName: String,
    val studyRole: StudyRole,
)