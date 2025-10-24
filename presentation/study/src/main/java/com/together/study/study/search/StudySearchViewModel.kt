package com.together.study.study.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.common.state.UiState
import com.together.study.common.type.study.StudySortingType
import com.together.study.study.model.ExploreStudyItem
import com.together.study.study.search.state.SearchFilterState
import com.together.study.study.search.state.StudySearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
internal class StudySearchViewModel @Inject constructor(

) : ViewModel() {
    private val _searchTerm = MutableStateFlow("")
    private val _activeStudyState =
        MutableStateFlow<UiState<List<ExploreStudyItem>>>(UiState.Loading)
    private val _resultStudyState =
        MutableStateFlow<UiState<List<ExploreStudyItem>>>(UiState.Loading)
    private val _searchFilterState = MutableStateFlow(SearchFilterState())

    val studySearchUiState: StateFlow<StudySearchUiState> = combine(
        _searchTerm, _activeStudyState, _resultStudyState, _searchFilterState,
    ) { searchTerm, activeStudyState, resultStudyState, searchFilterState ->
        StudySearchUiState(
            searchTerm = searchTerm,
            activeStudyState = activeStudyState,
            resultStudyState = resultStudyState,
            searchFilterState = searchFilterState,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = StudySearchUiState(
            searchTerm = "",
            activeStudyState = UiState.Loading,
            resultStudyState = UiState.Loading,
            searchFilterState = SearchFilterState(),
        )
    )

    suspend fun getActiveStudies() {
        // TODO : 추후 API 연결
        _activeStudyState.value = UiState.Success(
            listOf()
        )
    }

    suspend fun getResultStudies() {
        // TODO : 추후 API 연결
        _resultStudyState.value = UiState.Success(
            listOf()
        )
    }

    fun updateSearchTerm(new: String) = _searchTerm.update { new }

    fun updateSortOption(new: StudySortingType) =
        _searchFilterState.update { it.copy(sortOption = new) }

    fun updateIsJoinable() =
        _searchFilterState.update { it.copy(isJoinable = !_searchFilterState.value.isJoinable) }

    fun updateIsChallenge() =
        _searchFilterState.update { it.copy(isChallenge = !_searchFilterState.value.isChallenge) }
}
