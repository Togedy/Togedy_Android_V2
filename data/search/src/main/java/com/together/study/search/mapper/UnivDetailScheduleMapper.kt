package com.together.study.search.mapper

import com.together.study.search.dto.UnivAdmissionScheduleResponse
import com.together.study.search.dto.UnivDetailAdmissionMethodResponse
import com.together.study.search.dto.UnivDetailScheduleAddRequest
import com.together.study.search.dto.UnivDetailScheduleResponse
import com.together.study.search.model.AdmissionMethod
import com.together.study.search.model.UnivAdmissionSchedule
import com.together.study.search.model.UnivDetailAdmissionMethod
import com.together.study.search.model.UnivDetailSchedule

fun UnivDetailScheduleResponse.toDomain(): UnivDetailSchedule =
    UnivDetailSchedule(
        universityName = universityName,
        universityAdmissionType = universityAdmissionType,
        addedAdmissionMethodList = addedAdmissionMethodList,
        universityAdmissionMethodList = universityAdmissionMethodList.map { it.toDomain() }
    )

fun UnivDetailAdmissionMethodResponse.toDomain(): UnivDetailAdmissionMethod =
    UnivDetailAdmissionMethod(
        universityAdmissionMethod = universityAdmissionMethod,
        universityAdmissionMethodId = universityAdmissionMethodId,
        universityScheduleList = universityScheduleList.map { it.toDomain() }
    )

fun UnivAdmissionScheduleResponse.toDomain(): UnivAdmissionSchedule =
    UnivAdmissionSchedule(
        universityAdmissionStage = universityAdmissionStage,
        startDate = startDate,
        startTime = startTime,
        endDate = endDate,
        endTime = endTime
    )

fun AdmissionMethod.toData(): UnivDetailScheduleAddRequest =
    UnivDetailScheduleAddRequest(
        universityAdmissionMethodId = universityAdmissionMethodId,
    )

