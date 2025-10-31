package com.together.study.study.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudyAttendanceResponse(
    @SerialName("userId") val userId: Long,
    @SerialName("userName") val userName: String,
    @SerialName("studyTimeList") val studyTimeList: List<String?>,
)
