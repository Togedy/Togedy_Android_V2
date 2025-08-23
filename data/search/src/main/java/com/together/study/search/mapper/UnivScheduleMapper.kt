package com.together.study.search.mapper

import com.together.study.search.dto.UnivScheduleResponse
import com.together.study.search.model.UnivSchedule


fun UnivScheduleResponse.toDomain(): UnivSchedule =
    UnivSchedule(
        universityId = universityId,
        universityName = universityName,
        universityAdmissionType = universityAdmissionType,
        universityAdmissionMethodCount = universityAdmissionMethodCount,
        addedAdmissionMethodList = addedAdmissionMethodList
    )