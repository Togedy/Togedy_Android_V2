package com.together.study.search.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnivDetailScheduleAddRequest(
    @SerialName("universityAdmissionMethodId") val universityAdmissionMethodId: Int,
)
