package com.together.study.search.model

data class UnivScheduleList(
    val hasNext: Boolean,
    val schedules: List<UnivSchedule>
)

data class UnivSchedule(
    val universityId: Int,
    val universityName: String,
    val universityAdmissionType: String,
    val universityAdmissionMethodCount: Int,
    val addedAdmissionMethodList: List<String>
)

