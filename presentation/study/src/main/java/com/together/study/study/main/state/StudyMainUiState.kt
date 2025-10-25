package com.together.study.study.main.state

import androidx.compose.runtime.Immutable
import com.together.study.common.state.UiState
import com.together.study.common.type.study.StudySortingType
import com.together.study.study.model.ExploreStudyItem
import com.together.study.study.model.MyStudyInfo
import com.together.study.study.type.StudyTagType

@Immutable
data class StudyMainUiState(
    val myStudyState: UiState<MyStudyInfo>,
    val exploreStudyState: UiState<List<ExploreStudyItem>>,
    val exploreFilterState: ExploreFilterState,
) {
    val isLoaded: UiState<Unit>
        get() = when {
            myStudyState is UiState.Loading || exploreStudyState is UiState.Loading
                -> UiState.Loading

            myStudyState is UiState.Success && exploreStudyState is UiState.Success
                -> UiState.Success(Unit)

            myStudyState is UiState.Failure && exploreStudyState is UiState.Failure
                -> UiState.Failure("failed to load")

            else -> UiState.Empty
        }
}

data class ExploreFilterState(
    val tagFilters: List<StudyTagType> = listOf(StudyTagType.ENTIRE),
    val sortOption: StudySortingType = StudySortingType.RECENT,
    val isJoinable: Boolean = false,
    val isChallenge: Boolean = false,
)
