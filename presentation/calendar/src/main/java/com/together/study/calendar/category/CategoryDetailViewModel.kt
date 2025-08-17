package com.together.study.calendar.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.calendar.model.Category
import com.together.study.calendar.usecase.DeleteCategoryUseCase
import com.together.study.calendar.usecase.GetCategoryUseCase
import com.together.study.calendar.usecase.PatchCategoryUseCase
import com.together.study.calendar.usecase.PostCategoryUseCase
import com.together.study.common.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class CategoryDetailViewModel @Inject constructor(
    private val postCategoryUseCase: PostCategoryUseCase,
    private val getCategoryUseCase: GetCategoryUseCase,
    private val patchCategoryUseCase: PatchCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
) : ViewModel() {
    private val _categoryState = MutableStateFlow<UiState<List<Category>>>(UiState.Loading)
    val categoryState = _categoryState.asStateFlow()

    private var lastedCategoryItems = emptyList<Category>()


    fun fetchCategoryItems() = viewModelScope.launch {
        getCategoryUseCase()
            .onSuccess {
                updateState(UiState.Success(it))
                lastedCategoryItems = it
            }
            .onFailure { updateState(UiState.Failure(it.message.toString())) }
    }

    fun saveNewCategory(name: String, color: String) = viewModelScope.launch {
        postCategoryUseCase(name, color)
            .onFailure { Timber.tag("[okhttp] Category API - FAILURE").d("${it.message}") }

        val updatedList = lastedCategoryItems + Category(null, name, color)
        updateState(UiState.Success(updatedList))
    }

    fun updateCategory(new: Category) = viewModelScope.launch {
        patchCategoryUseCase(new)
            .onFailure { Timber.tag("[okhttp] Category API - FAILURE").d("${it.message}") }

        val updatedList = lastedCategoryItems.map { category ->
            if (category.categoryId == new.categoryId) {
                category.copy(categoryName = new.categoryName, categoryColor = new.categoryColor)
            } else category
        }
        updateState(UiState.Success(updatedList))
    }

    fun deleteCategory(categoryId: Long) = viewModelScope.launch {
        deleteCategoryUseCase(categoryId)
            .onFailure { Timber.tag("[okhttp] Category API - FAILURE").d("${it.message}") }

        val updatedList = lastedCategoryItems.filter { it.categoryId != categoryId }
        updateState(UiState.Success(updatedList))
    }

    private fun updateState(newState: UiState<List<Category>>) =
        _categoryState.update { newState }
}
