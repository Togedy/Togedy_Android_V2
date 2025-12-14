package com.together.study.study.mapper

import com.together.study.study.dto.StudyMemberBriefInfoResponse
import com.together.study.study.model.StudyMemberBriefInfo

fun List<StudyMemberBriefInfoResponse>.toDomain(): List<StudyMemberBriefInfo> =
    this.map { it.toStudyMemberBriefInfo() }

fun StudyMemberBriefInfoResponse.toStudyMemberBriefInfo(): StudyMemberBriefInfo =
    StudyMemberBriefInfo(
        userId = this.userId,
        userName = this.userName,
        userProfileImageUrl = this.userProfileImageUrl,
        studyRole = this.studyRole,
    )
