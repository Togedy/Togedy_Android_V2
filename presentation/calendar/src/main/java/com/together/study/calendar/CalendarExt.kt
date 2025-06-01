package com.together.study.calendar

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

fun generateCalendarWeeks(currentDate: LocalDate): List<List<LocalDate>> {
    val yearMonth = YearMonth.of(currentDate.year, currentDate.month)
    val firstOfMonth = yearMonth.atDay(1)
    val lastOfMonth = yearMonth.atEndOfMonth()

    var startDate = firstOfMonth
    while (startDate.dayOfWeek != DayOfWeek.SUNDAY) {
        startDate = startDate.minusDays(1)
    }

    var endDate = lastOfMonth
    while (endDate.dayOfWeek != DayOfWeek.SATURDAY) {
        endDate = endDate.plusDays(1)
    }

    val weeks = mutableListOf<List<LocalDate>>()
    var current = startDate
    while (current <= endDate) {
        val week = (0..6).map { current.plusDays(it.toLong()) }
        weeks.add(week)
        current = current.plusWeeks(1)
    }

    return weeks
}
