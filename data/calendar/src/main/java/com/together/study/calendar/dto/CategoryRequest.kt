package com.together.study.calendar.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryRequest(
    @SerialName("categoryName") val categoryName: String,
    @SerialName("categoryColor") val categoryColor: String,
)
