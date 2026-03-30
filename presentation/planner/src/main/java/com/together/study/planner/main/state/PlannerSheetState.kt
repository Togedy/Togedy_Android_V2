package com.together.study.planner.main.state

data class PlannerSheetState(
    val isSubjectOpen: Boolean = false,
    val isSubjectAddOpen: Boolean = false,
    val isCalendarOpen: Boolean = false,
)