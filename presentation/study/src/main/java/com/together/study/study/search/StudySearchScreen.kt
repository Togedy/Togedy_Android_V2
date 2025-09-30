package com.together.study.study.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.common.type.StudySortingType
import com.together.study.designsystem.R.drawable.ic_arrow_left_24
import com.together.study.designsystem.component.TogedySearchBar
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.study.component.SortingFilterSection
import com.together.study.study.component.StudyItem
import com.together.study.study.main.state.Study
import com.together.study.util.noRippleClickable

@Composable
fun StudySearchRoute(modifier: Modifier = Modifier) {
    var searchTerm by remember { mutableStateOf("") }
}

@Composable
fun StudySearchScreen(
    searchTerm: String,
    activeStudies: List<Study>,
    resultStudies: List<Study>,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onSearchTermChange: (String) -> Unit,
    onStudyItemClick: (Long) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.gray100)
            .padding(horizontal = 16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(ic_arrow_left_24),
                contentDescription = "뒤로 가기 버튼",
                modifier = Modifier
                    .size(24.dp)
                    .noRippleClickable(onBackClick),
            )

            Spacer(Modifier.width(4.dp))

            TogedySearchBar(
                placeholder = "스터디명, 태그로 검색해보세요",
                value = searchTerm,
                onValueChange = onSearchTermChange,
                onSearchClicked = {},
            )
        }

        EmptyResultScreen(
            activeStudies = activeStudies,
            onStudyItemClick = onStudyItemClick,
        )
    }
}

@Composable
fun EmptyResultScreen(
    activeStudies: List<Study>,
    modifier: Modifier = Modifier,
    onStudyItemClick: (Long) -> Unit,
) {
    Column(
        modifier = modifier.padding(top = 20.dp)
    ) {
        Text(
            text = "\uD83D\uDCDA 지금 열일하는 스터디",
            style = TogedyTheme.typography.body13b,
            color = TogedyTheme.colors.gray800,
        )

        Spacer(Modifier.height(12.dp))

        activeStudies.forEach { study ->
            StudyItem(
                study = study,
                modifier = Modifier.padding(bottom = 12.dp),
                onItemClick = { onStudyItemClick(study.studyId) },
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SuccessResultScreen(
    selectedSortingType: StudySortingType,
    isJoinable: Boolean,
    isChallenge: Boolean,
    resultStudies: List<Study>,
    modifier: Modifier = Modifier,
    onSortingClick: () -> Unit,
    onJoinableClick: () -> Unit,
    onChallengeClick: () -> Unit,
    onStudyItemClick: (Long) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        stickyHeader {
            SortingFilterSection(
                selectedSortingType = selectedSortingType,
                isJoinable = isJoinable,
                isChallenge = isChallenge,
                onSortingClick = onSortingClick,
                onJoinableClick = onJoinableClick,
                onChallengeClick = onChallengeClick,
            )
        }

        itemsIndexed(resultStudies) { _, study ->
            Box(
                modifier = Modifier.padding(vertical = 4.dp),
            ) {
                StudyItem(
                    study = study,
                    modifier = Modifier,
                    onItemClick = { onStudyItemClick(study.studyId) },
                )
            }
        }
    }
}

@Preview
@Composable
private fun StudySearchScreenPreview() {
    var searchTerm by remember { mutableStateOf("") }

    TogedyTheme {
        StudySearchScreen(
            searchTerm = searchTerm,
            activeStudies = listOf(Study.mock1, Study.mock1, Study.mock2),
            resultStudies = listOf(Study.mock1, Study.mock1, Study.mock2),
            onBackClick = {},
            onSearchTermChange = { searchTerm = it },
            onStudyItemClick = { },
        )
    }
}
