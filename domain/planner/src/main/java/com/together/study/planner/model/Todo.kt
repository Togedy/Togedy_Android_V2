package com.together.study.planner.model

data class Todo(
    val id: Long? = null,
    var content: String? = null,
    var state: Int = 0,
)
