package com.together.study.search.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnivDetailScheduleResponse(
    @SerialName("universityName") val universityName: String,
    @SerialName("universityAdmissionType") val universityAdmissionType: String,
    @SerialName("addedUniversityAdmissionMethodList") val addedAdmissionMethodList: List<String>,
    @SerialName("universityAdmissionMethodList") val universityAdmissionMethodList: List<UnivDetailAdmissionMethodResponse>
)

@Serializable
data class UnivDetailAdmissionMethodResponse(
    @SerialName("universityAdmissionMethod") val universityAdmissionMethod: String,
    @SerialName("universityAdmissionMethodId") val universityAdmissionMethodId: Int,
    @SerialName("universityScheduleList") val universityScheduleList: List<UnivAdmissionScheduleResponse>
)

@Serializable
data class UnivAdmissionScheduleResponse(
    @SerialName("universityAdmissionStage") val universityAdmissionStage: String,
    @SerialName("startDate") val startDate: String,
    @SerialName("startTime") val startTime: String,
    @SerialName("endDate") val endDate: String?,
    @SerialName("endTime") val endTime: String?
)

