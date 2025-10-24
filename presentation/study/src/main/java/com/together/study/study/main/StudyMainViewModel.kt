package com.together.study.study.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.common.state.UiState
import com.together.study.common.type.study.StudySortingType
import com.together.study.designsystem.component.tabbar.StudyMainTab
import com.together.study.study.main.state.ExploreFilterState
import com.together.study.study.main.state.MyStudyInfo
import com.together.study.study.main.state.Study
import com.together.study.study.main.state.StudyMainUiState
import com.together.study.study.model.StudyMainTimerInfo
import com.together.study.study.type.StudyTagType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class StudyMainViewModel @Inject constructor(

) : ViewModel() {
    private val _selectedTab = MutableStateFlow(StudyMainTab.MAIN)
    val selectedTab = _selectedTab.asStateFlow()

    private val _myStudyState = MutableStateFlow<UiState<MyStudyInfo>>(UiState.Loading)
    private val _exploreStudyState = MutableStateFlow<UiState<List<Study>>>(UiState.Loading)
    private val _exploreFilterState = MutableStateFlow(ExploreFilterState())

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

    fun getStudyMainInfo() = viewModelScope.launch {
        getMyStudyInfo()
        getExploreInfo()
    }

    suspend fun getMyStudyInfo() {
        // TODO : 추후 API 연결
        _myStudyState.value = UiState.Success(
            MyStudyInfo(
                studyMainTimerInfo = StudyMainTimerInfo.mock1,
                studyList = emptyList(),
            )
        )
    }

    suspend fun getExploreInfo() {
        _exploreStudyState.value = UiState.Success(
            listOf()
        )
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
