package com.together.study.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    @SerialName("isSuccess") val isSuccess: Boolean,
    @SerialName("response") val response: T,
)
