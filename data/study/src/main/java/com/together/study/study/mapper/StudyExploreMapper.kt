package com.together.study.study.mapper

import com.together.study.study.dto.ActiveMemberResponse
import com.together.study.study.dto.ExploreStudyInfoResponse
import com.together.study.study.dto.ExploreStudyItemResponse
import com.together.study.study.dto.MyStudyInfoResponse
import com.together.study.study.dto.MyStudyItemResponse
import com.together.study.study.model.ExploreStudyInfo
import com.together.study.study.model.ExploreStudyItem
import com.together.study.study.model.MyStudyInfo
import com.together.study.study.model.MyStudyItem
import com.together.study.study.model.MyStudyItem.Companion.ActiveMember
import com.together.study.study.model.StudyMainTimerInfo

fun MyStudyInfoResponse.toDomain(): MyStudyInfo =
    MyStudyInfo(
        studyMainTimerInfo = StudyMainTimerInfo(
            hasChallenge = hasChallenge,
            goalTime = goalTime,
            studyTime = studyTime,
            achievement = achievement,
        ),
        studyList = studyList.map { it.toMyStudyItem() }
    )

fun MyStudyItemResponse.toMyStudyItem(): MyStudyItem =
    MyStudyItem(
        studyId = studyId,
        studyType = studyType,
        challengeGoalTime = challengeGoalTime,
        challengeAchievement = challengeAchievement,
        studyName = studyName,
        completedMemberCount = completedMemberCount,
        studyMemberCount = studyMemberCount,
        activeMemberList = activeMemberList.map { it.toActiveMember() },
    )

fun ActiveMemberResponse.toActiveMember(): ActiveMember =
    ActiveMember(
        userName = userName,
        userProfileImageUrl = userProfileImageUrl,
    )

fun ExploreStudyInfoResponse.toDomain(): ExploreStudyInfo =
    ExploreStudyInfo(
        hasNext = hasNext,
        studies = studyList.map { it.toExploreStudyItem() }
    )

fun ExploreStudyItemResponse.toExploreStudyItem(): ExploreStudyItem =
    ExploreStudyItem(
        studyId = studyId,
        studyType = studyType,
        studyName = studyName,
        studyDescription = studyDescription,
        studyTag = studyTag,
        studyLeaderImageUrl = studyLeaderImageUrl,
        studyTier = studyTier,
        studyMemberCount = studyMemberCount,
        studyMemberLimit = studyMemberLimit,
        studyImageUrl = studyImageUrl,
        isNewlyCreated = isNewlyCreated,
        lastActivatedAt = lastActivatedAt,
        hasPassword = hasPassword,
        challengeGoalTime = challengeGoalTime,
    )
