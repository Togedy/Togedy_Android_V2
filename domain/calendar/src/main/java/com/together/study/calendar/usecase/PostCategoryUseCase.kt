package com.together.study.calendar.usecase

import com.together.study.calendar.repository.CategoryRepository
import javax.inject.Inject

class PostCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
) {
    suspend operator fun invoke(name: String, color: String): Result<Unit> {
        return categoryRepository.postCategory(name, color)
    }
}
