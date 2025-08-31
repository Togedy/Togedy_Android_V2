package com.together.study.util

import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
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

fun LocalDate?.formatToScheduleDate(): String {
    if (this == null) return ""
    return "${YearMonth.from(this).monthValue}.${this.dayOfMonth} " +
            "${this.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN)}"
}
