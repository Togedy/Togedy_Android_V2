package com.together.study.common.type.study

enum class StudyType() {
    CHALLENGE,
    NORMAL;

    companion object {
        fun get(studyType: String): StudyType {
            return try {
                valueOf(studyType.uppercase())
            } catch (e: Exception) {
                NORMAL
            }
        }
    }
}
