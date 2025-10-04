package com.together.study.common.type

enum class StudyType() {
    CHALLENGE,
    NORMAL;

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
