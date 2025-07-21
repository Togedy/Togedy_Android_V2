package com.together.study.calendar.model

data class Schedule(
    val scheduleId: Long? = null,
    val scheduleType: String,
    val scheduleName: String,
    val startDate: String,
    val startTime: String? = null,
    val endTime: String? = null,
    val endDate: String? = null,
    val universityAdmissionType: String = "",
    val universityAdmissionStage: String = "",
    val category: Category,
) {
    companion object { /* TODO: 추후 삭제 예정 */
        val mock = listOf(
            Schedule(
                scheduleId = 1,
                scheduleType = "university",
                scheduleName = "고려대학교 원서접수 기간",
                startDate = "2025-06-02",
                endDate = "2025-06-03",
                universityAdmissionStage = "원서접수",
                universityAdmissionType = "수시",
                category = Category(1, "가", "CATEGORY_COLOR3"),
            ),
            Schedule(
                scheduleId = 2,
                scheduleType = "user",
                scheduleName = "영어 단어 암기",
                startDate = "2025-06-02",
                endDate = null,
                category = Category(1, "가", "CATEGORY_COLOR2"),
            ),
            Schedule(
                scheduleId = 3,
                scheduleType = "user",
                scheduleName = "수학 문제풀이",
                startDate = "2025-06-04",
                endDate = null,
                category = Category(1, "가", "CATEGORY_COLOR9"),
            ),
            Schedule(
                scheduleId = 3,
                scheduleType = "user",
                scheduleName = "수학 문제풀이",
                startDate = "2025-06-04",
                endDate = null,
                category = Category(1, "가", "CATEGORY_COLOR9"),
            ),
            Schedule(
                scheduleId = 3,
                scheduleType = "user",
                scheduleName = "수학 문제풀이",
                startDate = "2025-06-04",
                endDate = null,
                category = Category(1, "가", "CATEGORY_COLOR9"),
            ),
            Schedule(
                scheduleId = 4,
                scheduleType = "university",
                scheduleName = "한양대학교 면접",
                startDate = "2025-06-06",
                endDate = null,
                universityAdmissionStage = "면접",
                universityAdmissionType = "정시",
                category = Category(1, "가", "CATEGORY_COLOR5"),
            ),
            Schedule(
                scheduleId = 5,
                scheduleType = "user",
                scheduleName = "자습 시간",
                startDate = "2025-06-07",
                endDate = null,
                category = Category(1, "가", "CATEGORY_COLOR12"),
            ),
            Schedule(
                scheduleId = 6,
                scheduleType = "user",
                scheduleName = "자습 시간",
                startDate = "2025-06-03",
                endDate = null,
                category = Category(1, "가", "CATEGORY_COLOR12"),
            ),
            Schedule(
                scheduleId = 7,
                scheduleType = "user",
                scheduleName = "자습 시간",
                startDate = "2025-06-07",
                endDate = "2025-06-12",
                category = Category(1, "가", "CATEGORY_COLOR10"),
            )
        )
    }
}
