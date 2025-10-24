package com.together.study.common.type.study

enum class StudySortingType(
    val label: String,
    val request: String,
) {
    RECENT("최신순", "latest"),
    MIN_COUNT("참여인원 적은 순", "most"),
    MAX_COUNT("참여인원 많은 순", "least"),
}
