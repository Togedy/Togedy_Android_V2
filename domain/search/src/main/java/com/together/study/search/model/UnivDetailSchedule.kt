package com.together.study.search.model

data class UnivDetailSchedule(
    val universityName: String,
    val universityAdmissionType: String,
    val addedAdmissionMethodList: List<String>,
    val universityAdmissionMethodList: List<UnivDetailAdmissionMethod>
)

data class UnivDetailAdmissionMethod(
    val universityAdmissionMethod: String,
    val universityAdmissionMethodId: Int,
    val universityScheduleList: List<UnivAdmissionSchedule>
)

data class UnivAdmissionSchedule(
    val universityAdmissionStage: String,
    val startDate: String,
    val startTime: String,
    val endDate: String?,
    val endTime: String?
)
