package com.together.study.study.main.state

import androidx.compose.runtime.Immutable
import com.together.study.common.state.UiState
import com.together.study.common.type.study.StudySortingType
import com.together.study.common.type.study.StudyTagType
import com.together.study.study.model.ExploreStudyItem
import com.together.study.study.model.MyStudyInfo
import com.together.study.study.model.StudyBadge

@Immutable
data class StudyMainUiState(
    val myStudyState: UiState<MyStudyInfo>,
    val exploreStudyState: UiState<List<ExploreStudyItem>>,
    val exploreFilterState: ExploreFilterState,
    val badgeState: UiState<StudyBadgeState>,
)

data class ExploreFilterState(
    val tagFilters: List<StudyTagType> = listOf(StudyTagType.ENTIRE),
    val sortOption: StudySortingType = StudySortingType.RECENT,
    val isJoinable: Boolean = false,
    val isChallenge: Boolean = false,
)

data class StudyBadgeState(
    val recentBadge: StudyBadge?,
    val badges: List<StudyBadge>,
)
