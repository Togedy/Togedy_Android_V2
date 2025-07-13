package com.together.study.calendar.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.calendar.model.Category
import com.together.study.calendar.repository.CategoryRepository
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
    private val categoryRepository: CategoryRepository,
) : ViewModel() {
    private val _categoryState = MutableStateFlow<UiState<List<Category>>>(UiState.Loading)
    val categoryState = _categoryState.asStateFlow()

    private var lastedCategoryItems = emptyList<Category>()


    fun fetchCategoryItems() = viewModelScope.launch {
        categoryRepository.getCategoryItems()
            .onSuccess {
                updateState(UiState.Success(it))
                lastedCategoryItems = it
            }
            .onFailure { updateState(UiState.Failure(it.message.toString())) }
    }

    fun saveNewCategory(new: Category) = viewModelScope.launch {
        categoryRepository.postCategory(new.categoryName!!, new.categoryColor!!)
            .onSuccess { Timber.tag("[okhttp] Category API - SUCCESS").d("$it") }
            .onFailure { Timber.tag("[okhttp] Category API - FAILURE").d("${it.message}") }

        val updatedList = lastedCategoryItems + new
        updateState(UiState.Success(updatedList))
    }

    fun updateCategory(new: Category) = viewModelScope.launch {
        categoryRepository.patchCategory(category = new)
            .onSuccess { Timber.tag("[okhttp] Category API - SUCCESS").d("$it") }
            .onFailure { Timber.tag("[okhttp] Category API - FAILURE").d("${it.message}") }

        val updatedList = lastedCategoryItems.map { category ->
            if (category.categoryId == new.categoryId) {
                category.copy(categoryName = new.categoryName, categoryColor = new.categoryColor)
            } else category
        }
        updateState(UiState.Success(updatedList))
    }

    fun deleteCategory(categoryId: Long) = viewModelScope.launch {
        categoryRepository.deleteCategory(categoryId)
            .onSuccess { Timber.tag("[okhttp] Category API - SUCCESS").d("$it") }
            .onFailure { Timber.tag("[okhttp] Category API - FAILURE").d("${it.message}") }

        val updatedList = lastedCategoryItems.filter { it.categoryId != categoryId }
        updateState(UiState.Success(updatedList))
    }

    private fun updateState(newState: UiState<List<Category>>) =
        _categoryState.update { newState }
}
