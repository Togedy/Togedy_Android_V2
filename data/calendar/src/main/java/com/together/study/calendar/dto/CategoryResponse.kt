package com.together.study.calendar.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    @SerialName("categoryId") val categoryId: Long? = null,
    @SerialName("categoryName") val categoryName: String? = null,
    @SerialName("categoryColor") val categoryColor: String? = null,
)
