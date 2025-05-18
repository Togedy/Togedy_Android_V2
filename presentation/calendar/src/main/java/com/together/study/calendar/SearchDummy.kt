package com.together.study.calendar

data class SearchScheduleData(
    val universityName: String,
    val admissionType: String,
    val admissionList: List<SearchAdmissionData>,
    val isAdded: Boolean = false
)

data class SearchAdmissionData(
    val admissionMethod: String,
    val universityScheduleList: List<SearchUniversityData>
)


data class SearchUniversityData(
    val universityScheduleId: Int,
    val startDate: String,
    val endDate: String,
    val admissionStage: String
)

fun dummyScheduleList(): List<SearchScheduleData> {
    return listOf(
        SearchScheduleData(
            universityName = "건국대학교(서울캠퍼스)",
            admissionType = "수시",
            isAdded = true,
            admissionList = listOf(
                SearchAdmissionData(
                    admissionMethod = "학생부 종합 전형",
                    universityScheduleList = listOf(
                        SearchUniversityData(1, "2024-09-09 10:00:00", "2024-09-12 17:00:00", "원서접수"),
                        SearchUniversityData(2, "2024-09-12 17:00:00", "2024-09-12 17:00:00", "서류제출"),
                        SearchUniversityData(3, "2024-09-01", "2024-09-15", "합격발표")
                    )
                ),
                SearchAdmissionData(
                    admissionMethod = "학생부 교과 전형",
                    universityScheduleList = listOf(
                        SearchUniversityData(4, "2024-09-01", "2024-09-10", "원서접수"),
                        SearchUniversityData(5, "2024-09-10", "2024-09-15", "서류제출"),
                        SearchUniversityData(6, "2024-09-20", "2024-09-25", "합격발표")
                    )
                )
            )
        ),
        SearchScheduleData(
            universityName = "서울대학교(서울캠퍼스)",
            admissionType = "정시",
            isAdded = false,
            admissionList = listOf(
                SearchAdmissionData(
                    admissionMethod = "정시 일반 전형",
                    universityScheduleList = listOf(
                        SearchUniversityData(7, "2024-10-01", "2024-10-05", "원서접수"),
                        SearchUniversityData(8, "2024-10-06", "2024-10-10", "서류제출"),
                        SearchUniversityData(9, "2024-10-15", "2024-10-20", "합격발표")
                    )
                )
            )
        ),
        SearchScheduleData(
            universityName = "고려대학교(서울캠퍼스)",
            admissionType = "정시",
            isAdded = true,
            admissionList = listOf(
                SearchAdmissionData(
                    admissionMethod = "정시 일반 전형",
                    universityScheduleList = listOf(
                        SearchUniversityData(10, "2024-09-01", "2024-09-05", "원서접수"),
                        SearchUniversityData(11, "2024-09-06", "2024-09-10", "서류제출"),
                        SearchUniversityData(12, "2024-09-15", "2024-09-20", "합격발표")
                    )
                )
            )
        ),
        SearchScheduleData(
            universityName = "연세대학교(서울캠퍼스)",
            admissionType = "정시",
            isAdded = false,
            admissionList = listOf(
                SearchAdmissionData(
                    admissionMethod = "정시 일반 전형",
                    universityScheduleList = listOf(
                        SearchUniversityData(13, "2024-09-01", "2024-09-05", "원서접수"),
                        SearchUniversityData(14, "2024-09-06", "2024-09-10", "서류제출"),
                        SearchUniversityData(15, "2024-09-15", "2024-09-20", "합격발표")
                    )
                )
            )
        ),
        SearchScheduleData(
            universityName = "중앙대학교(서울캠퍼스)",
            admissionType = "정시",
            isAdded = true,
            admissionList = listOf(
                SearchAdmissionData(
                    admissionMethod = "정시 일반 전형",
                    universityScheduleList = listOf(
                        SearchUniversityData(16, "2024-09-01", "2024-09-05", "원서접수"),
                        SearchUniversityData(17, "2024-09-06", "2024-09-10", "서류제출"),
                        SearchUniversityData(18, "2024-09-15", "2024-09-20", "합격발표")
                    )
                )
            )
        ),
        SearchScheduleData(
            universityName = "경희대학교(서울캠퍼스)",
            admissionType = "정시",
            isAdded = false,
            admissionList = listOf(
                SearchAdmissionData(
                    admissionMethod = "정시 일반 전형",
                    universityScheduleList = listOf(
                        SearchUniversityData(19, "2024-09-01", "2024-09-05", "원서접수"),
                        SearchUniversityData(20, "2024-09-06", "2024-09-10", "서류제출"),
                        SearchUniversityData(21, "2024-09-15", "2024-09-20", "합격발표")
                    )
                )
            )
        )
    )
}
