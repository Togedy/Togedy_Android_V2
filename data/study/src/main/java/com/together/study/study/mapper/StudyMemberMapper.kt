package com.together.study.study.mapper

import com.together.study.study.dto.DailyPlannerResponse
import com.together.study.study.dto.MonthlyStudyTimeResponse
import com.together.study.study.dto.PlanResponse
import com.together.study.study.dto.StudyMemberPlannerResponse
import com.together.study.study.dto.StudyMemberProfileResponse
import com.together.study.study.dto.StudyMemberTimeBlockResponse
import com.together.study.study.model.StudyMemberPlanner
import com.together.study.study.model.StudyMemberPlanner.DailyPlanner
import com.together.study.study.model.StudyMemberPlanner.Plan
import com.together.study.study.model.StudyMemberProfile
import com.together.study.study.model.StudyMemberTimeBlocks
import com.together.study.study.model.StudyMemberTimeBlocks.MonthlyStudyTime


fun StudyMemberProfileResponse.toDomain(): StudyMemberProfile =
    StudyMemberProfile(
        userName = userName,
        userStatus = userStatus,
        userProfileImageUrl = userProfileImageUrl,
        userProfileMessage = userProfileMessage ?: "",
        totalStudyTime = totalStudyTime,
        attendanceStreak = attendanceStreak,
        elapsedDays = elapsedDays,
    )

fun StudyMemberTimeBlockResponse.toDomain(): StudyMemberTimeBlocks =
    StudyMemberTimeBlocks(
        studyTimeCount = studyTimeCount,
        monthlyStudyTimeList = monthlyStudyTimeList.map { it.toMonthlyStudyTime() }
    )

fun MonthlyStudyTimeResponse.toMonthlyStudyTime(): MonthlyStudyTime =
    MonthlyStudyTime(
        year = year,
        month = month,
        studyTimeList = studyTimeList,
    )

fun StudyMemberPlannerResponse.toDomain(): StudyMemberPlanner =
    StudyMemberPlanner(
        isMyPlanner = isMyPlanner,
        isPlannerVisible = isPlannerVisible,
        completedPlanCount = completedPlanCount,
        totalPlanCount = totalPlanCount,
        dailyPlanner = dailyPlanner.map { it.toDailyPlanner() }
    )

fun DailyPlannerResponse.toDailyPlanner(): DailyPlanner =
    DailyPlanner(
        studyCategoryName = studyCategoryName,
        planList = planList.map { it.toPlan() }
    )

fun PlanResponse.toPlan(): Plan =
    Plan(
        planName = planName,
        planStatus = planStatus,
    )
