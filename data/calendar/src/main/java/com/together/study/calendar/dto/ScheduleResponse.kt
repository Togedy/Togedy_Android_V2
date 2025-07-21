package com.together.study.calendar.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MonthlyScheduleListResponse(
    @SerialName("monthlyScheduleList") val monthlyScheduleList: List<ScheduleResponse>,
)

@Serializable
data class DailyScheduleListResponse(
    @SerialName("dailyScheduleList") val dailyScheduleList: List<ScheduleResponse>,
)

@Serializable
data class ScheduleResponse(
    @SerialName("scheduleId") val scheduleId: Long? = null,
    @SerialName("scheduleType") val scheduleType: String,
    @SerialName("scheduleName") val scheduleName: String,
    @SerialName("startDate") val startDate: String,
    @SerialName("startTime") val startTime: String? = null,
    @SerialName("endTime") val endTime: String? = null,
    @SerialName("endDate") val endDate: String? = null,
    @SerialName("universityAdmissionType") val universityAdmissionType: String = "",
    @SerialName("universityAdmissionStage") val universityAdmissionStage: String = "",
    @SerialName("category") val category: CategoryResponse,
)
