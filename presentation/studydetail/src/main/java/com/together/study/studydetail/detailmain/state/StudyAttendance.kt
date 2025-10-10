package com.together.study.studydetail.detailmain.state

data class StudyAttendance(
    val userId: Long,
    val userName: String,
    val studyTimeList: List<String?>,
) {
    companion object {
        val mock = StudyAttendance(
            userId = 1,
            userName = "유저1",
            studyTimeList = listOf(
                "11:00:00",
                "08:00:00",
                "05:00:00",
                null,
                "01:13:00",
                null,
                null
            )
        )
        val mock2 = StudyAttendance(
            userId = 2,
            userName = "유저2",
            studyTimeList = listOf(
                null,
                null,
                null,
                null,
                null,
                "10:00:00",
                null
            )
        )
    }
}
