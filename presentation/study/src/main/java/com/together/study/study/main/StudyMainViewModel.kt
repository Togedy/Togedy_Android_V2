package com.together.study.study.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.common.state.UiState
import com.together.study.common.type.study.StudySortingType
import com.together.study.designsystem.component.tabbar.StudyMainTab
import com.together.study.study.main.state.ExploreFilterState
import com.together.study.study.main.state.StudyMainUiState
import com.together.study.study.model.ExploreStudyFilter
import com.together.study.study.model.ExploreStudyItem
import com.together.study.study.model.MyStudyInfo
import com.together.study.study.repository.StudyExploreRepository
import com.together.study.study.type.StudyTagType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
internal class StudyMainViewModel @Inject constructor(
    private val studyExploreRepository: StudyExploreRepository,
) : ViewModel() {
    private val _selectedTab = MutableStateFlow(StudyMainTab.MAIN)
    val selectedTab = _selectedTab.asStateFlow()

    private val _myStudyState = MutableStateFlow<UiState<MyStudyInfo>>(UiState.Loading)
    private val _exploreStudyState =
        MutableStateFlow<UiState<List<ExploreStudyItem>>>(UiState.Loading)
    private val _exploreFilterState = MutableStateFlow(ExploreFilterState())
    val exploreFilterState = _exploreFilterState.asStateFlow()

    val studyMainUiState: StateFlow<StudyMainUiState> = combine(
        _myStudyState, _exploreStudyState, _exploreFilterState
    ) { myStudyState, exploreStudyState, exploreFilterState ->
        StudyMainUiState(
            myStudyState = myStudyState,
            exploreStudyState = exploreStudyState,
            exploreFilterState = exploreFilterState,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = StudyMainUiState(
            myStudyState = UiState.Loading,
            exploreStudyState = UiState.Loading,
            exploreFilterState = ExploreFilterState(),
        )
    )

    suspend fun getMyStudyInfo() {
        studyExploreRepository.getMyStudyInfo()
            .onSuccess { _myStudyState.value = UiState.Success(it) }
            .onFailure { _myStudyState.value = UiState.Failure(it.message.toString()) }
    }

    suspend fun getExploreInfo() {
        val filterValue = exploreFilterState.value.tagFilters
        val tagFilter =
            if (filterValue == listOf(StudyTagType.ENTIRE)) null
            else filterValue.map { it.label }

        studyExploreRepository.getExploreStudyItems(
            with(_exploreFilterState.value) {
                ExploreStudyFilter(
                    tag = tagFilter,
                    filter = sortOption.request,
                    joinable = isJoinable,
                    challenge = isChallenge,
                    page = null,
                    size = null
                )
            }
        )
            .onSuccess { _exploreStudyState.value = UiState.Success(it.studies) }
            .onFailure { _exploreStudyState.value = UiState.Failure(it.message.toString()) }
    }

    fun updateSelectedTab(new: StudyMainTab) {
        _selectedTab.value = new
    }

    fun updateTagFilters(new: StudyTagType) {
        val current = _exploreFilterState.value.tagFilters

        if (current == listOf(new)) return

        val newFilters = when {
            current == listOf(StudyTagType.ENTIRE) || new == StudyTagType.ENTIRE -> listOf(new)
            new in current -> current.filter { it != new }
            else -> current + new
        }.ifEmpty { listOf(StudyTagType.ENTIRE) }

        _exploreFilterState.update { it.copy(tagFilters = newFilters) }
    }

    fun updateSortOption(new: StudySortingType) =
        _exploreFilterState.update { it.copy(sortOption = new) }

    fun updateIsJoinable() =
        _exploreFilterState.update { it.copy(isJoinable = !_exploreFilterState.value.isJoinable) }

    fun updateIsChallenge() =
        _exploreFilterState.update { it.copy(isChallenge = !_exploreFilterState.value.isChallenge) }
}
