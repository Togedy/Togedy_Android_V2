package com.together.study.planner.type

import androidx.compose.ui.graphics.Color

data class StudyStatusUiModel(
    val text: String,
    val imageRes: Int,
    val backgroundColor: Color? = null,
)
