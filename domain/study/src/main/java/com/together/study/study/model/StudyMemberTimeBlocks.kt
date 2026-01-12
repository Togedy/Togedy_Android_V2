package com.together.study.study.model

data class StudyMemberTimeBlocks(
    val studyTimeCount: Int,
    val monthlyStudyTimeList: List<MonthlyStudyTime>,
) {
    data class MonthlyStudyTime(
        val year: Int,
        val month: Int,
        val studyTimeList: List<Int>,
    )
}
