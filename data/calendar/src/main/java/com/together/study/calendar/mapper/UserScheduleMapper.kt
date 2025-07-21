package com.together.study.calendar.mapper

import com.together.study.calendar.dto.UserScheduleRequest
import com.together.study.calendar.dto.UserScheduleResponse
import com.together.study.calendar.model.UserSchedule

fun UserSchedule.toData(): UserScheduleRequest =
    UserScheduleRequest(
        userScheduleName = userScheduleName,
        startDate = startDate,
        startTime = startTime,
        endDate = endDate,
        endTime = endTime,
        categoryId = category.categoryId!!,
        memo = memo,
        dDay = dDay,
    )

fun UserScheduleResponse.toDomain(): UserSchedule =
    UserSchedule(
        userScheduleName = userScheduleName,
        startDate = startDate,
        startTime = startTime,
        endDate = endDate,
        endTime = endTime,
        category = category.toDomain(),
        memo = memo,
        dDay = dDay,
    )
