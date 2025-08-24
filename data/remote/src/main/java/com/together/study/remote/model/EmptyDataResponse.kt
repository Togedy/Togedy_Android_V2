package com.together.study.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmptyDataResponse(
    @SerialName("isSuccess")
    val isSuccess: Boolean,
)
