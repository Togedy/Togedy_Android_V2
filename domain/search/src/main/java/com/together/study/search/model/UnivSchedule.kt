package com.together.study.search.model

data class UnivSchedule(
    val universityId: Int,
    val universityName: String,
    val universityAdmissionType: String,
    val universityAdmissionMethodCount: Int,
    val addedAdmissionMethodList: List<String>
)