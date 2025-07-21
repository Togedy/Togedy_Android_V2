package com.together.study.calendar.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserScheduleRequest(
    @SerialName("userScheduleName") val userScheduleName: String,
    @SerialName("startDate") val startDate: String,
    @SerialName("startTime") val startTime: String? = null,
    @SerialName("endDate") val endDate: String? = null,
    @SerialName("endTime") val endTime: String? = null,
    @SerialName("categoryId") val categoryId: Long,
    @SerialName("memo") val memo: String? = null,
    @SerialName("d-day") val dDay: Boolean,
)
