package com.together.study.planner.usecase

import com.together.study.planner.type.AbsentRange
import com.together.study.planner.type.StreakRange
import com.together.study.planner.type.StudyStatus

fun getStudyStatus(
    daysSinceLastStudy: Int,
    currentStreakDays: Int
): StudyStatus {
    return when {
        currentStreakDays > 0 -> {
            StudyStatus.Streak(StreakRange.from(currentStreakDays))
        }
        daysSinceLastStudy > 0 -> {
            StudyStatus.Absent(AbsentRange.from(daysSinceLastStudy))
        }
        else -> {
            throw IllegalArgumentException("둘 다 0일 수 없음")
        }
    }
}
