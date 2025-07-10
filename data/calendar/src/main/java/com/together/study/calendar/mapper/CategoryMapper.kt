package com.together.study.calendar.mapper

import com.together.study.calendar.dto.CategoryResponse
import com.together.study.calendar.model.Category

fun CategoryResponse.toDomain(): Category =
    Category(
        categoryId = categoryId,
        categoryName = categoryName,
        categoryColor = categoryColor,
    )
