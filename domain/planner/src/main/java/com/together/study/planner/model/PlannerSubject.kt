package com.together.study.planner.model

data class PlannerSubject(
    val id: Long? = null,
    val name: String = "",
    val color: String = "",
    val todoItems: List<Todo>?,
)
