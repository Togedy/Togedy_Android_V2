package com.together.study.planner.type

enum class AbsentRange {
    DAY_1,
    DAY_2_3,
    DAY_4_7,
    DAY_8_30,
    DAY_31_50,
    DAY_51_99,
    DAY_100_199,
    DAY_200_PLUS;

    companion object {
        fun from(days: Int): AbsentRange {
            return when (days) {
                1 -> DAY_1
                in 2..3 -> DAY_2_3
                in 4..7 -> DAY_4_7
                in 8..30 -> DAY_8_30
                in 31..50 -> DAY_31_50
                in 51..99 -> DAY_51_99
                in 100..199 -> DAY_100_199
                else -> DAY_200_PLUS
            }
        }
    }
}
