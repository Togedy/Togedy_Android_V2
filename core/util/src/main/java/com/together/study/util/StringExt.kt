package com.together.study.util

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun String?.toLocalDate(): LocalDate? {
    return try {
        this?.let {
            LocalDate.parse(it, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        }
    } catch (e: Exception) {
        null
    }
}

fun String.toLocalTime(): LocalTime {
    return LocalTime.parse(this, DateTimeFormatter.ofPattern("HH:mm"))
}

fun String.toLocalTimeWithSecond(): LocalTime {
    return LocalTime.parse(this, DateTimeFormatter.ofPattern("HH:mm:ss"))
}
