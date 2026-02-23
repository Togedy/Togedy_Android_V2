package com.together.study.common.type.planner

enum class PlannerSubjectColor(val color: Long) {
    SUBJECT_COLOR1(0xFFD9A398),
    SUBJECT_COLOR2(0xFFFFB985),
    SUBJECT_COLOR3(0xFFD3C3A8),
    SUBJECT_COLOR4(0xFF84A3B7),
    SUBJECT_COLOR5(0xFFA0CAF0),
    SUBJECT_COLOR6(0xFFACACF2),
    SUBJECT_COLOR7(0xFF565249),
    SUBJECT_COLOR8(0xFF5EB8B8),
    SUBJECT_COLOR9(0xFFFF875C),
    SUBJECT_COLOR10(0xFFF0A5BE),
    SUBJECT_COLOR11(0xFFE77283),
    SUBJECT_COLOR12(0xFF6F72A0),
    UNKNOWN_COLOR(0xFFEAEAEA);

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

fun String?.toPlannerSubjectColorEnum(): PlannerSubjectColor {
    return PlannerSubjectColor.fromStringToCategoryColorEnum(this)
}

fun String?.toPlannerSubjectColorOrDefault(): Long {
    return PlannerSubjectColor.fromString(this).color
}
