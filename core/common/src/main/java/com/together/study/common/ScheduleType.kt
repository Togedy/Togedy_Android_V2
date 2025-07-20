package com.together.study.common

enum class ScheduleType(
    val label: String,
) {
    UNIVERSITY(label = "university"),
    USER(label = "user");


    companion object {
        fun get(scheduleType: String): ScheduleType {
            return try {
                ScheduleType.valueOf(scheduleType.uppercase())
            } catch (e: Exception) {
                USER
            }
        }
    }
}