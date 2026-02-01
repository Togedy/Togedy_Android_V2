package com.together.study.planner.type

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

enum class PlannerSubjectColor(val color: Color) {
    SUBJECT_COLOR1(Color(0xFFD9A398)),
    SUBJECT_COLOR2(Color(0xFFFFB985)),
    SUBJECT_COLOR3(Color(0xFFD3C3A8)),
    SUBJECT_COLOR4(Color(0xFF84A3B7)),
    SUBJECT_COLOR5(Color(0xFFA0CAF0)),
    SUBJECT_COLOR6(Color(0xFFACACF2)),
    SUBJECT_COLOR7(Color(0xFF565249)),
    SUBJECT_COLOR8(Color(0xFF5EB8B8)),
    SUBJECT_COLOR9(Color(0xFFFF875C)),
    SUBJECT_COLOR10(Color(0xFFF0A5BE)),
    SUBJECT_COLOR11(Color(0xFFE77283)),
    SUBJECT_COLOR12(Color(0xFF6F72A0)),
    UNKNOWN_COLOR(Color.LightGray);

    companion object {
        fun fromString(name: String?): PlannerSubjectColor {
            return try {
                valueOf(name ?: "SUBJECT_COLOR1")
            } catch (e: IllegalArgumentException) {
                SUBJECT_COLOR1
            }
        }

        fun fromStringToCategoryColorEnum(name: String?): PlannerSubjectColor {
            return try {
                valueOf(name ?: "SUBJECT_COLOR1")
            } catch (e: IllegalArgumentException) {
                SUBJECT_COLOR1
            }
        }
    }
}

@Composable
fun String?.toPlannerSubjectColorEnum(): PlannerSubjectColor {
    return PlannerSubjectColor.fromStringToCategoryColorEnum(this)
}

@Composable
fun String?.toPlannerSubjectColorOrDefault(): Color {
    return PlannerSubjectColor.fromString(this).color
}

@Composable
fun Color.toBackgroundColorOrDefault(): Color {
    return this.copy(alpha = 0.05f)
}
