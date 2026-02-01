package com.together.study.util

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.time.temporal.WeekFields
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

fun LocalDate.formatToYearMonthDate(): String =
    "${this.year}년 ${this.monthValue}월 ${this.dayOfMonth}일"

fun LocalDate.getDaysInMonthGrid(): List<String> {
    val yearMonth = YearMonth.of(this.year, this.month)
    val firstDayOfMonth = yearMonth.atDay(1)
    val weekFields = WeekFields.of(DayOfWeek.SUNDAY, 1)
    val firstDayOfWeekIndex = firstDayOfMonth.get(weekFields.dayOfWeek())
    val daysInMonth = yearMonth.lengthOfMonth()
    val daysList = mutableListOf<String>()

    repeat(firstDayOfWeekIndex - 1) {
        daysList.add("")
    }

    (1..daysInMonth).forEach { day ->
        daysList.add(day.toString())
    }

    return daysList
}
