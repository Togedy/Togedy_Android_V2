package com.together.study.planner.type

enum class StreakRange {
    DAY_1,
    DAY_2_7,
    DAY_8_59,
    DAY_60_99,
    DAY_100_199,
    DAY_200_PLUS;

    companion object {
        fun from(days: Int): StreakRange {
            return when (days) {
                1 -> DAY_1
                in 2..7 -> DAY_2_7
                in 8..59 -> DAY_8_59
                in 60..99 -> DAY_60_99
                in 100..199 -> DAY_100_199
                else -> DAY_200_PLUS
            }
        }
    }
}
