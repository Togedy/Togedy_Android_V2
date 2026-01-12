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

/**
 * String을 특정 Enum Class의 값으로 변환합니다.
 *
 * @param fallback 변환 실패 시 반환할 기본 Enum 값
 * @param ignoreCase 대소문자 무시 여부 (기본값: true)
 * @return 해당하는 Enum 값, 또는 변환 실패 시 fallback 값
 */
inline fun <reified T : Enum<T>> String.toEnum(
    fallback: T,
    ignoreCase: Boolean = true,
): T {
    return try {
        val enumString = if (ignoreCase) this.uppercase() else this
        java.lang.Enum.valueOf(T::class.java, enumString)
    } catch (e: Exception) {
        fallback
    }
}
