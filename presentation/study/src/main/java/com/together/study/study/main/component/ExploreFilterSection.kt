package com.together.study.study.main.component

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.together.study.common.type.study.StudySortingType
import com.together.study.common.type.study.StudyTagType
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.study.component.SortingFilterSection
import com.together.study.study.component.TagFilterSection

@Composable
internal fun ExploreFilterSection(
    selectedFilter: List<StudyTagType>,
    selectedSortingType: StudySortingType,
    isJoinable: Boolean,
    isChallenge: Boolean,
    modifier: Modifier = Modifier,
    onFilterClick: (StudyTagType) -> Unit,
    onSortingClick: () -> Unit,
    onJoinableClick: () -> Unit,
    onChallengeClick: () -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        TagFilterSection(
            selectedFilter = selectedFilter,
            onFilterClick = onFilterClick,
        )

        SortingFilterSection(
            selectedSortingType = selectedSortingType,
            isJoinable = isJoinable,
            isChallenge = isChallenge,
            onSortingClick = onSortingClick,
            onJoinableClick = onJoinableClick,
            onChallengeClick = onChallengeClick,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ExploreFilterSectionPreview() {
    TogedyTheme {
        ExploreFilterSection(
            selectedFilter = listOf(StudyTagType.ENTIRE),
            selectedSortingType = StudySortingType.RECENT,
            isJoinable = true,
            isChallenge = false,
            modifier = Modifier,
            onFilterClick = { },
            onSortingClick = { },
            onJoinableClick = { },
            onChallengeClick = { },
        )
    }
}
