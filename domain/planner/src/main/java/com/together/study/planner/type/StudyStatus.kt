package com.together.study.planner.type

sealed class StudyStatus {

    data class Absent(val range: AbsentRange) : StudyStatus()
    data class Streak(val range: StreakRange) : StudyStatus()
}