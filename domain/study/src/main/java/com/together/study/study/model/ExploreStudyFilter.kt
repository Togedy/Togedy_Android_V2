package com.together.study.study.model

data class ExploreStudyFilter(
    val tag: List<String>?,
    val filter: String,
    val joinable: Boolean,
    val challenge: Boolean,
    val page: Int?,
    val size: Int?,
)
