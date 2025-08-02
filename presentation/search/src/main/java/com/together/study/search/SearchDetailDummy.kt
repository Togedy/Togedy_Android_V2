package com.together.study.search

data class SearchDetailDummy(
    val universityName: String,
    val universityAdmissionType: String,
    val universityAdmissionMethodCount: Int,
    val addedAdmissionMethodList: List<String>,
    val universityAdmissionMethodList: List<UniversityAdmissionMethod>
)

data class UniversityAdmissionMethod(
    val universityAdmissionMethod: String,
    val universityAdmissionMethodId: Int,
    val universityScheduleList: List<UniversitySchedule>
)

data class UniversitySchedule(
    val universityAdmissionStage: String,
    val startDate: String,
    val startTime: String,
    val endDate: String?,
    val endTime: String?
)

// 더미 데이터 생성 함수
fun dummySearchDetailList(): List<SearchDetailDummy> {
    return listOf()
}
