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
val universityData = SearchDetailDummy(
    universityName = "건국대학교(서울캠퍼스)",
    universityAdmissionType = "수시",
    universityAdmissionMethodCount = 4,
    addedAdmissionMethodList = listOf(
        "논술(KU논술우수자)",
        "학생부교과(KU지역균형)",
        "학생부종합전형"
    ),
    universityAdmissionMethodList = listOf(
        UniversityAdmissionMethod(
            universityAdmissionMethod = "논술(KU논술우수자)",
            universityAdmissionMethodId = 1,
            universityScheduleList = listOf(
                UniversitySchedule(
                    universityAdmissionStage = "원서접수",
                    startDate = "2024-09-09",
                    startTime = "10:00:00",
                    endDate = "2024-09-12",
                    endTime = "17:00:00"
                ),
                UniversitySchedule(
                    universityAdmissionStage = "서류제출",
                    startDate = "2024-09-09",
                    startTime = "10:00:00",
                    endDate = "2024-09-13",
                    endTime = "17:00:00"
                ),
                UniversitySchedule(
                    universityAdmissionStage = "합격발표",
                    startDate = "2024-12-13",
                    startTime = "14:00:00",
                    endDate = null,
                    endTime = null
                )
            )
        ),
        UniversityAdmissionMethod(
            universityAdmissionMethod = "실기/실적(KU연기우수자, KU체육특기자)",
            universityAdmissionMethodId = 2,
            universityScheduleList = listOf(
                UniversitySchedule(
                    universityAdmissionStage = "원서접수",
                    startDate = "2024-09-09",
                    startTime = "10:00:00",
                    endDate = "2024-09-12",
                    endTime = "17:00:00"
                ),
                UniversitySchedule(
                    universityAdmissionStage = "서류제출",
                    startDate = "2024-09-09",
                    startTime = "10:00:00",
                    endDate = "2024-09-13",
                    endTime = "17:00:00"
                ),
                UniversitySchedule(
                    universityAdmissionStage = "합격발표",
                    startDate = "2024-11-22",
                    startTime = "14:00:00",
                    endDate = null,
                    endTime = null
                )
            )
        ),
        UniversityAdmissionMethod(
            universityAdmissionMethod = "학생부교과(KU지역균형)",
            universityAdmissionMethodId = 3,
            universityScheduleList = listOf(
                UniversitySchedule(
                    universityAdmissionStage = "원서접수",
                    startDate = "2024-09-09",
                    startTime = "10:00:00",
                    endDate = "2024-09-12",
                    endTime = "17:00:00"
                ),
                UniversitySchedule(
                    universityAdmissionStage = "서류제출",
                    startDate = "2024-09-09",
                    startTime = "10:00:00",
                    endDate = "2024-09-13",
                    endTime = "17:00:00"
                ),
                UniversitySchedule(
                    universityAdmissionStage = "합격발표",
                    startDate = "2024-12-13",
                    startTime = "14:00:00",
                    endDate = null,
                    endTime = null
                )
            )
        ),
        UniversityAdmissionMethod(
            universityAdmissionMethod = "학생부종합전형",
            universityAdmissionMethodId = 4,
            universityScheduleList = listOf(
                UniversitySchedule(
                    universityAdmissionStage = "원서접수",
                    startDate = "2024-09-09",
                    startTime = "10:00:00",
                    endDate = "2024-09-12",
                    endTime = "17:00:00"
                ),
                UniversitySchedule(
                    universityAdmissionStage = "서류제출",
                    startDate = "2024-09-09",
                    startTime = "10:00:00",
                    endDate = "2024-09-13",
                    endTime = "17:00:00"
                ),
                UniversitySchedule(
                    universityAdmissionStage = "합격발표",
                    startDate = "2024-12-13",
                    startTime = "14:00:00",
                    endDate = null,
                    endTime = null
                )
            )
        )
    )
)
