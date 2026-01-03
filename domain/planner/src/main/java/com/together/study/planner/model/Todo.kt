package com.together.study.planner.model

data class Todo(
    val id: Long,
    var content: String?,
    var state: Int = 0,
)
