package com.together.study.timer.dto

import kotlinx.serialization.Serializable

@Serializable
data class SubjectTimerResponse(
    val subjectId: Long,
    val subjectName: String,
    val subjectColor: String,
    val studyTime: Long,
)
