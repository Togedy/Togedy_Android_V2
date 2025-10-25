package com.together.study.study.search.state

import androidx.compose.runtime.Immutable
import com.together.study.common.state.UiState
import com.together.study.common.type.study.StudySortingType
import com.together.study.study.model.ExploreStudyItem

@Immutable
data class StudySearchUiState(
    val searchTerm: String,
    val activeStudyState: UiState<List<ExploreStudyItem>>,
    val resultStudyState: UiState<List<ExploreStudyItem>>,
    val searchFilterState: SearchFilterState,
) {
    val isLoaded: UiState<Unit>
        get() = when {
            activeStudyState is UiState.Loading || resultStudyState is UiState.Loading
                -> UiState.Loading

            activeStudyState is UiState.Failure && resultStudyState is UiState.Failure
                -> UiState.Failure("failed to load")

            activeStudyState is UiState.Success && searchTerm.isEmpty()
                -> UiState.Empty

            resultStudyState is UiState.Success && searchTerm.isNotEmpty()
                -> UiState.Success(Unit)

            else -> UiState.Empty
        }
}

data class SearchFilterState(
    val sortOption: StudySortingType = StudySortingType.RECENT,
    val isJoinable: Boolean = false,
    val isChallenge: Boolean = false,
)
