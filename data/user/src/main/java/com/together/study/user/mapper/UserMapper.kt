package com.together.study.user.mapper

import com.together.study.user.dto.UserInfoResponse
import com.together.study.user.dto.UserSettingInfoResponse
import com.together.study.user.dto.UserStudyInfoResponse
import com.together.study.user.model.UserInfo
import com.together.study.user.model.UserSettingInfo
import com.together.study.user.model.UserStudyInfo

fun UserInfoResponse.toDomain(): UserInfo =
    UserInfo(
        userName = userName,
        userEmail = userEmail,
        userProfileImageUrl = userProfileImageUrl,
        totalStudyTime = totalStudyTime,
        attendanceStreak = attendanceStreak,
        studies = studies.map { it.toDomain() },
    )

fun UserStudyInfoResponse.toDomain(): UserStudyInfo =
    UserStudyInfo(
        studyId = studyId,
        studyName = studyName,
        studyImageUrl = studyImageUrl,
        isCompleted = isCompleted,
        completedMemberCount = completedMemberCount,
        studyMemberCount = studyMemberCount,
    )

fun UserSettingInfoResponse.toDomain(): UserSettingInfo =
    UserSettingInfo(
        pushNotificationEnabled = pushNotificationEnabled,
        marketingConsented = marketingConsented,
        userEmail = userEmail,
    )
