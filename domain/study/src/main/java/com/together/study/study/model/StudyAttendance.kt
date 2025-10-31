package com.together.study.study.model

data class StudyAttendance(
    val userId: Long,
    val userName: String,
    val studyTimeList: List<String?>,
)
