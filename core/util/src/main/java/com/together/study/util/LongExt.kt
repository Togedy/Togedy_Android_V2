package com.together.study.util

import androidx.compose.ui.graphics.Color

/*
 Long 값을 실제 컬러값으로 변환해주는 함수
 */
fun Long.asColor(): Color {
    return Color(this)
}
