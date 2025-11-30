package com.together.study.study.model

data class StudyMemberPlanner(
    val isMyPlanner: Boolean,
    val isPlannerVisible: Boolean,
    val completedPlanCount: Int?,
    val totalPlanCount: Int?,
    val dailyPlanner: List<DailyPlanner>?,
) {
    data class DailyPlanner(
        val studyCategoryName: String,
        val planList: List<Plan>,
    )

    data class Plan(
        val planName: String,
        val planStatus: String,
    )
}