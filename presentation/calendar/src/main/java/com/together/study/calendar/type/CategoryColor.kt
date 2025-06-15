package com.together.study.calendar.type

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

enum class CategoryColor(val color: Color) {
    CATEGORY_COLOR1(Color(0xFFD9A398)),
    CATEGORY_COLOR2(Color(0xFFFFB985)),
    CATEGORY_COLOR3(Color(0xFFD3C3A8)),
    CATEGORY_COLOR4(Color(0xFF84A3B7)),
    CATEGORY_COLOR5(Color(0xFFA0CAF0)),
    CATEGORY_COLOR6(Color(0xFFACACF2)),
    CATEGORY_COLOR7(Color(0xFF565249)),
    CATEGORY_COLOR8(Color(0xFF5EB8B8)),
    CATEGORY_COLOR9(Color(0xFFFF875C)),
    CATEGORY_COLOR10(Color(0xFFF0A5BE)),
    CATEGORY_COLOR11(Color(0xFFE77283)),
    CATEGORY_COLOR12(Color(0xFF6F72A0)),
    UNKNOWN_COLOR(Color.LightGray);

    companion object {
        fun fromString(name: String?): CategoryColor {
            return try {
                valueOf(name ?: "UNKNOWN_COLOR")
            } catch (e: IllegalArgumentException) {
                UNKNOWN_COLOR
            }
        }
    }
}

@Composable
fun String?.toCategoryColorOrDefault(): Color {
    return CategoryColor.fromString(this).color
}

@Composable
fun Color.toBackgroundColorOrDefault(): Color {
    return this.copy(alpha = 0.05f)
}
