package com.together.study.planner.mapper

import com.together.study.planner.dto.response.DailyPlannerInfoResponse
import com.together.study.planner.dto.response.DailyStatisticsResponse
import com.together.study.planner.dto.response.DailyTimeTableResponse
import com.together.study.planner.dto.response.PlannerSubjectResponse
import com.together.study.planner.dto.response.TimeTableResponse
import com.together.study.planner.dto.response.TodoResponse
import com.together.study.planner.model.DailyPlannerInfo
import com.together.study.planner.model.DailyStatistics
import com.together.study.planner.model.PlannerSubject
import com.together.study.planner.model.TimeTable
import com.together.study.planner.model.Todo

fun DailyPlannerInfoResponse.toDomain(): DailyPlannerInfo {
    return DailyPlannerInfo(
        date = this.date,
        hasDday = this.hasDday,
        userScheduleName = this.userScheduleName,
        remainingDays = this.remainingDays,
        totalStudyTime = this.totalStudyTime,
        plannerImage = this.plannerImage
    )
}

fun PlannerSubjectResponse.toDomain(): PlannerSubject {
    return PlannerSubject(
        subjectId = this.subjectId,
        subjectName = this.subjectName,
        subjectColor = this.subjectColor,
        subjectStudyTime = this.subjectStudyTime,
        tasks = this.taskList.map { it.toDomain() },
    )
}

fun DailyTimeTableResponse.toDomain(): List<TimeTable> {
    return this.timeTableList.map { it.toDomain() }
}

fun TimeTableResponse.toDomain(): TimeTable {
    return TimeTable(
        startTime = this.startTime,
        endTime = this.endTime,
        subjectColor = this.subjectColor,
    )
}

fun TodoResponse.toDomain(): Todo {
    return Todo(
        id = this.taskId,
        content = this.taskName,
        isChecked = this.isChecked,
    )
}

fun DailyStatisticsResponse.toDomain(): DailyStatistics {
    return DailyStatistics(
        daysSinceLastStudy = this.daysSinceLastStudy,
        currentStreakDays = this.currentStreakDays,
        weeklyReview = this.weeklyReview,
        monthlyReview = this.monthlyReview,
    )
}
