package com.together.study.planner.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class MonthlyHeatmapResponse(
    val heatmapList: List<Int>,
)
