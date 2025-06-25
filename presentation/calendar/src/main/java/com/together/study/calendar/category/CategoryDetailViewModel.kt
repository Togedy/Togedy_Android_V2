package com.together.study.calendar.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.calendar.model.Category
import com.together.study.common.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CategoryDetailViewModel @Inject constructor(

) : ViewModel() {
    private val _categoryState = MutableStateFlow<UiState<List<Category>>>(UiState.Loading)
    val categoryState = _categoryState.asStateFlow()

    private var lastedCategoryItems = _categoryState.value

    init {
        fetchCategoryItems()
    }

    private fun fetchCategoryItems() = viewModelScope.launch {
        // TODO : API 연결
        updateState(
            UiState.Success(
                Category.mockList
            )
        )

        lastedCategoryItems = _categoryState.value
    }

    fun saveNewCategory(new: Category) = viewModelScope.launch {
        // TODO : API 연결
        val currentList = (_categoryState.value as? UiState.Success)?.data.orEmpty()
        val updatedList = currentList + new
        updateState(UiState.Success(updatedList))
    }

    fun updateCategory(new: Category) = viewModelScope.launch {
        // TODO : API 연결
        val currentList = (_categoryState.value as? UiState.Success)?.data.orEmpty()

        val updatedList = currentList.map { category ->
            if (category.categoryId == new.categoryId) {
                category.copy(categoryName = new.categoryName, categoryColor = new.categoryColor)
            } else category
        }

        updateState(UiState.Success(updatedList))
    }

    private fun updateState(newState: UiState<List<Category>>) =
        _categoryState.update { newState }
}
