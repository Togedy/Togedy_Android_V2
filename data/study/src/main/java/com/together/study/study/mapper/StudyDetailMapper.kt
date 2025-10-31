package com.together.study.study.mapper

import com.together.study.study.dto.StudyAttendanceResponse
import com.together.study.study.dto.StudyDetailInfoResponse
import com.together.study.study.dto.StudyMemberResponse
import com.together.study.study.model.StudyAttendance
import com.together.study.study.model.StudyDetailInfo
import com.together.study.study.model.StudyMember
import com.together.study.study.type.StudyRole
import com.together.study.study.type.UserStatus
import com.together.study.util.toEnum

fun StudyDetailInfoResponse.toDomain(): StudyDetailInfo =
    StudyDetailInfo(
        isJoined = isJoined,
        isStudyLeader = isStudyLeader,
        studyName = studyName,
        studyDescription = studyDescription,
        studyImageUrl = studyImageUrl,
        studyType = studyType,
        studyTag = studyTag,
        studyTier = studyTier,
        studyMemberCount = studyMemberCount,
        completedMemberCount = completedMemberCount,
        studyMemberLimit = studyMemberLimit,
        studyPassword = studyPassword,
    )

@JvmName("studyMemberResponseToDomain")
fun List<StudyMemberResponse>.toDomain(): List<StudyMember> = this.map { it.toStudyMember() }

fun StudyMemberResponse.toStudyMember(): StudyMember =
    StudyMember(
        userId = userId,
        userName = userName,
        userProfileImageUrl = userProfileImageUrl ?: "",
        studyRole = studyRole.toEnum(StudyRole.MEMBER),
        userStatus = userStatus.toEnum(UserStatus.ACTIVE),
        studyTime = studyTime,
        lastActivatedAt = lastActivatedAt,
    )

@JvmName("studyAttendanceResponseToDomain")
fun List<StudyAttendanceResponse>.toDomain(): List<StudyAttendance> =
    this.map { it.toStudyAttendance() }

fun StudyAttendanceResponse.toStudyAttendance(): StudyAttendance =
    StudyAttendance(
        userId = userId,
        userName = userName,
        studyTimeList = studyTimeList,
    )
