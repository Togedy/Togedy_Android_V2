package com.together.study.search.mapper

import com.together.study.search.dto.UnivScheduleListResponse
import com.together.study.search.dto.UnivScheduleResponse
import com.together.study.search.model.UnivSchedule
import com.together.study.search.model.UnivScheduleList

fun UnivScheduleListResponse.toDomain(): UnivScheduleList =
    UnivScheduleList(
        hasNext = hasNext,
        schedules = universityList.map { it.toUnivSchedule() }
    )

fun UnivScheduleResponse.toUnivSchedule(): UnivSchedule =
    UnivSchedule(
        universityId = universityId,
        universityName = universityName,
        universityAdmissionType = universityAdmissionType,
        universityAdmissionMethodCount = universityAdmissionMethodCount,
        addedAdmissionMethodList = addedAdmissionMethodList
    )