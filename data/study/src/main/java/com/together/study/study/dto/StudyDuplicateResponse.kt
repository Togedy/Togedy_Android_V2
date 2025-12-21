package com.together.study.study.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudyDuplicateResponse(
    @SerialName("isDuplicate") val isDuplicate: Boolean,
)

