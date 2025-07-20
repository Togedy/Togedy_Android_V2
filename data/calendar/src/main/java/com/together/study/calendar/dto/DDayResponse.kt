package com.together.study.calendar.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DDayResponse(
    @SerialName("hasDday") val hasDday: Boolean,
    @SerialName("userScheduleName") val userScheduleName: String? = null,
    @SerialName("remainingDays") val remainingDays: Int?,
)
