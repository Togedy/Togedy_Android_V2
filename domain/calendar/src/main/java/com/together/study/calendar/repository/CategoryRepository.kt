package com.together.study.calendar.repository

import com.together.study.calendar.model.Category

interface CategoryRepository {
    suspend fun postCategory(name: String, color: String): Result<Unit>
    suspend fun getCategoryItems(): Result<List<Category>>
    suspend fun patchCategory(category: Category): Result<Unit>
    suspend fun deleteCategory(categoryId: Long): Result<Unit>
}
