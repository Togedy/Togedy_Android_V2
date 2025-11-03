package com.together.study.study.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.common.state.UiState
import com.together.study.common.type.study.StudySortingType
import com.together.study.study.model.ExploreStudyFilter
import com.together.study.study.model.ExploreStudyItem
import com.together.study.study.repository.StudyExploreRepository
import com.together.study.study.search.state.SearchFilterState
import com.together.study.study.search.state.StudySearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class StudySearchViewModel @Inject constructor(
    private val studyExploreRepository: StudyExploreRepository,
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
        studyExploreRepository.getPopularStudyItems()
            .onSuccess { _activeStudyState.value = UiState.Success(it) }
            .onFailure { _activeStudyState.value = UiState.Failure(it.message.toString()) }
    }

    fun getResultStudies() = viewModelScope.launch {
        _resultStudyState.value = UiState.Loading
        studyExploreRepository.getExploreStudyItems(
            with(_searchFilterState.value) {
                ExploreStudyFilter(
                    tag = null,
                    filter = sortOption.request,
                    joinable = isJoinable,
                    challenge = isChallenge,
                    name = _searchTerm.value,
                    page = null,
                    size = null,
                )
            }
        )
            .onSuccess { _resultStudyState.value = UiState.Success(it.studies) }
            .onFailure { _resultStudyState.value = UiState.Failure(it.message.toString()) }
    }

    fun updateSearchTerm(new: String) = viewModelScope.launch {
        _searchTerm.update { new }
        getResultStudies()
    }

    fun updateSortOption(new: StudySortingType) =
        _searchFilterState.update { it.copy(sortOption = new) }

    fun updateIsJoinable() =
        _searchFilterState.update { it.copy(isJoinable = !_searchFilterState.value.isJoinable) }

    fun updateIsChallenge() =
        _searchFilterState.update { it.copy(isChallenge = !_searchFilterState.value.isChallenge) }
}
