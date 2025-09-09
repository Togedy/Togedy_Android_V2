package com.together.study.calendar.usecase

import com.together.study.calendar.model.Category
import com.together.study.calendar.repository.CategoryRepository
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
) {
    suspend operator fun invoke(): Result<List<Category>> {
        return categoryRepository.getCategoryItems()
    }
}
