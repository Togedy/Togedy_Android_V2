package com.together.study.calendar.mapper

import com.together.study.calendar.dto.CategoryResponse
import com.together.study.calendar.model.Category

fun CategoryResponse.toDomain(): Category =
    Category(
        categoryId = categoryId,
        categoryName = categoryName,
        categoryColor = categoryColor,
    )

fun List<CategoryResponse>.toDomain(): List<Category> =
    this.map { it.toDomain() }
