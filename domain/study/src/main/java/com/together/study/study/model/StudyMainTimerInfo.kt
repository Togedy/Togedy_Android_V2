package com.together.study.study.model

data class StudyMainTimerInfo(
    val hasChallenge: Boolean,
    val goalTime: String?,
    val studyTime: String?,
    val achievement: Int?,
) {
    companion object {
        val mock1 = StudyMainTimerInfo(true, "10:00:00", "10:00:00", 0)
        val mock2 = StudyMainTimerInfo(false, null, "01:00:00", 0)
    }
}
