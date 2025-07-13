package com.together.study.calendar.repositoryimpl

import com.together.study.calendar.datasouce.CategoryDataSource
import com.together.study.calendar.dto.CategoryRequest
import com.together.study.calendar.mapper.toDomain
import com.together.study.calendar.model.Category
import com.together.study.calendar.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDataSource: CategoryDataSource,
) : CategoryRepository {
    override suspend fun postCategory(
        name: String,
        color: String,
    ): Result<Boolean> =
        runCatching {
            categoryDataSource.postCategory(CategoryRequest(name, color)).response
        }

    override suspend fun getCategoryItems(): Result<List<Category>> =
        runCatching {
            val response = categoryDataSource.getCategoryItems().response
            response.toDomain()
        }

    override suspend fun patchCategory(category: Category): Result<Boolean> =
        runCatching {
            with(category) {
                categoryDataSource.patchCategory(
                    categoryId = categoryId!!,
                    request = CategoryRequest(categoryName!!, categoryColor!!)
                ).response
            }
        }

    override suspend fun deleteCategory(categoryId: Long): Result<Boolean> =
        runCatching {
            categoryDataSource.deleteCategory(categoryId).response
        }
}
