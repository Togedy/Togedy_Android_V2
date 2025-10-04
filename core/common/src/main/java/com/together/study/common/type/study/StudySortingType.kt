package com.together.study.common.type.study

enum class StudySortingType(
    val label: String,
) {
    RECENT("최신순"),
    MIN_COUNT("참여인원 적은 순"),
    MAX_COUNT("참여인원 많은 순"),
}
