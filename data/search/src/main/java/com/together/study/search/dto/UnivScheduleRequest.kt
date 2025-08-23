package com.together.study.search.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnivScheduleRequest(
    @SerialName("name") val name: String,
    @SerialName("admission-type") val admissionType: String,
    @SerialName("page") val page: Int,
    @SerialName("size") val size: Int,
)
