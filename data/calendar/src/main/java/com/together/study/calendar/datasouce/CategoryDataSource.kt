package com.together.study.calendar.datasouce

import com.together.study.calendar.dto.CategoryRequest
import com.together.study.calendar.service.CategoryService
import javax.inject.Inject

class CategoryDataSource @Inject constructor(
    private val categoryService: CategoryService,
) {
    suspend fun postCategory(request: CategoryRequest) =
        categoryService.postCategory(request)

    suspend fun getCategoryItems() =
        categoryService.getCategoryItems()

    suspend fun patchCategory(categoryId: Long, request: CategoryRequest) =
        categoryService.patchCategory(categoryId, request)

    suspend fun deleteCategory(categoryId: Long) =
        categoryService.deleteCategory(categoryId)
}
