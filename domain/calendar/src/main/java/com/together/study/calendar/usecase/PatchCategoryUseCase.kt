package com.together.study.calendar.usecase

import com.together.study.calendar.model.Category
import com.together.study.calendar.repository.CategoryRepository
import javax.inject.Inject

class PatchCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
) {
    suspend operator fun invoke(category: Category): Result<Unit> {
        return categoryRepository.patchCategory(category)
    }
}
