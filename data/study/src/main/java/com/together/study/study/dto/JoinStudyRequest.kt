package com.together.study.study.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JoinStudyRequest(
    @SerialName("studyPassword") val studyPassword: String?,
)
