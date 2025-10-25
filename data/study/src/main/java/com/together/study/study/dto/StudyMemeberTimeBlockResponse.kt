package com.together.study.study.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudyMemberTimeBlockResponse(
    @SerialName("studyTimeCount") val studyTimeCount: Int,
    @SerialName("monthlyStudyTimeList") val monthlyStudyTimeList: List<MonthlyStudyTimeResponse>,
)

@Serializable
data class MonthlyStudyTimeResponse(
    @SerialName("year") val year: Int,
    @SerialName("month") val month: Int,
    @SerialName("studyTimeList") val studyTimeList: List<Int>,
)
