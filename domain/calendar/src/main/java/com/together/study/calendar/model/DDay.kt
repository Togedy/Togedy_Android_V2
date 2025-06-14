package com.together.study.calendar.model

data class DDay(
    val hasDday: Boolean,
    val userScheduleName: String? = null,
    val remainingDays: Int?,
) {
    companion object {
        val mock = DDay(
            hasDday = true,
            userScheduleName = "수능",
            remainingDays = 99,
        )
    }
}
