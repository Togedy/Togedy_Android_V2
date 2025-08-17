package com.together.study.calendar.usecase

import com.together.study.calendar.repository.CategoryRepository
import javax.inject.Inject

class DeleteCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
) {
    suspend operator fun invoke(categoryId: Long): Result<Unit> {
        return categoryRepository.deleteCategory(categoryId)
    }
}
