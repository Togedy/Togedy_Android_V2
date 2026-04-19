package com.together.study.timer.model

data class Timer (
    val timerId: Long,
    val startTime: String,
    val endTime: String? = null,
)