package com.together.study.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmptyDataResponse(
    @SerialName("status")
    val status: Int,
    @SerialName("message")
    val message: String,
)
