package com.together.study.util

import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

fun LocalTime?.toScheduleFormat(): String {
    if (this == null) return ""
    val formatter = DateTimeFormatter.ofPattern("a hh:mm", Locale.KOREAN)
    return this.format(formatter)
}

fun LocalTime.formatToDefaultLocalTime(): LocalTime {
    val roundedTime = this.truncatedTo(ChronoUnit.HOURS)
    return LocalTime.of(roundedTime.hour.toInt(), 0)
}
