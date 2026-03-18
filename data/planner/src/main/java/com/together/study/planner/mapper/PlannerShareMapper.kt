package com.together.study.planner.mapper

import com.together.study.planner.dto.response.ShareInfoResponse
import com.together.study.planner.model.ShareInfo

fun ShareInfoResponse.toDomain(): ShareInfo {
    return ShareInfo(
        date = this.date,
        hasDday = this.hasDday,
        userScheduleName = this.userScheduleName,
        remainingDays = this.remainingDays,
        totalStudyTime = this.totalStudyTime,
        image = this.image,
        plannerItems = this.plannerItemList.map { it.toDomain() },
        timeTables = this.timeTableList.map { it.toDomain() },
    )
}
