package com.together.study.search.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnivScheduleListResponse(
    @SerialName("hasNext") val hasNext: Boolean,
    @SerialName("universityList") val universityList: List<UnivScheduleResponse>,
)

@Serializable
data class UnivScheduleResponse(
    @SerialName("universityId") val universityId: Int,
    @SerialName("universityName") val universityName: String,
    @SerialName("universityAdmissionType") val universityAdmissionType: String,
    @SerialName("universityAdmissionMethodCount") val universityAdmissionMethodCount: Int,
    @SerialName("addedAdmissionMethodList") val addedAdmissionMethodList: List<String>,
)
