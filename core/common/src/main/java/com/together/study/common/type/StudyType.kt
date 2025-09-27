package com.together.study.common.type

enum class StudyType(
    val label: String,
) {
    CHALLENGE("challenge"),
    NORMAL("normal");

    companion object {
        fun get(studyType: String): StudyType {
            return try {
                StudyType.valueOf(studyType.uppercase())
            } catch (e: Exception) {
                NORMAL
            }
        }
    }
}
