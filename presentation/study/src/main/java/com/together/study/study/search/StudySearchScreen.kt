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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.together.study.common.state.UiState
import com.together.study.common.type.study.StudySortingType
import com.together.study.designsystem.R.drawable.ic_arrow_left_24
import com.together.study.designsystem.component.TogedySearchBar
import com.together.study.designsystem.component.loading.TogedyLoadingScreen
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.study.component.SortBottomSheet
import com.together.study.study.component.SortingFilterSection
import com.together.study.study.component.StudyItem
import com.together.study.study.model.ExploreStudyItem
import com.together.study.study.search.state.SearchFilterState
import com.together.study.study.search.state.StudySearchUiState
import com.together.study.util.noRippleClickable

@Composable
internal fun StudySearchRoute(
    onBackClick: () -> Unit,
    onStudyDetailNavigate: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: StudySearchViewModel = hiltViewModel(),
) {
    val uiState by viewModel.studySearchUiState.collectAsStateWithLifecycle()
    val filterState by viewModel.searchFilterState.collectAsStateWithLifecycle()


    LaunchedEffect(Unit) {
        viewModel.getActiveStudies()
    }

    LaunchedEffect(filterState) {
        viewModel.getResultStudies()
    }

    StudySearchScreen(
        uiState = uiState,
        filterState = filterState,
        modifier = modifier,
        onBackClick = onBackClick,
        onSearchTermChange = viewModel::updateSearchTerm,
        onStudyItemClick = onStudyDetailNavigate,
        onSortOptionClick = viewModel::updateSortOption,
        onJoinableClick = viewModel::updateIsJoinable,
        onChallengeClick = viewModel::updateIsChallenge,
    )
}

@Composable
fun StudySearchScreen(
    uiState: StudySearchUiState,
    filterState: SearchFilterState,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onSearchTermChange: (String) -> Unit,
    onStudyItemClick: (Long) -> Unit,
    onSortOptionClick: (StudySortingType) -> Unit,
    onJoinableClick: () -> Unit,
    onChallengeClick: () -> Unit,
) {
    var isSortBottomSheetVisible by remember { mutableStateOf(false) }

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
                value = uiState.searchTerm,
                onValueChange = onSearchTermChange,
                onSearchClicked = { onSearchTermChange(uiState.searchTerm) },
            )
        }

        if (uiState.searchTerm.isNotEmpty()) {
            with(filterState) {
                SortingFilterSection(
                    selectedSortingType = sortOption,
                    isJoinable = isJoinable,
                    isChallenge = isChallenge,
                    onSortingClick = { isSortBottomSheetVisible = true },
                    onJoinableClick = onJoinableClick,
                    onChallengeClick = onChallengeClick,
                    modifier = Modifier.background(TogedyTheme.colors.gray100),
                )
            }
        }

        when (uiState.isLoaded) {
            is UiState.Empty -> {
                EmptySearchTermScreen(
                    activeStudies = (uiState.activeStudyState as UiState.Success).data,
                    modifier = Modifier,
                    onStudyItemClick = onStudyItemClick,
                )
            }

            is UiState.Failure -> {}
            is UiState.Loading -> TogedyLoadingScreen()
            is UiState.Success<*> -> {
                with(filterState) {
                    SuccessResultScreen(
                        resultStudies = (uiState.resultStudyState as UiState.Success).data,
                        searchTerm = uiState.searchTerm,
                        onStudyItemClick = onStudyItemClick,
                    )
                }
            }
        }
    }

    if (isSortBottomSheetVisible) {
        SortBottomSheet(
            selectedSortOption = filterState.sortOption,
            onDismissRequest = { isSortBottomSheetVisible = false },
            onSortOptionClick = {
                onSortOptionClick(it)
                isSortBottomSheetVisible = false
            },
        )
    }
}

@Composable
fun EmptySearchTermScreen(
    activeStudies: List<ExploreStudyItem>,
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
    resultStudies: List<ExploreStudyItem>,
    searchTerm: String,
    modifier: Modifier = Modifier,
    onStudyItemClick: (Long) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
    ) {
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

        if (resultStudies.isEmpty()) {
            item {
                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Text(
                        text = "${searchTerm}에 대한 검색 결과가 없습니다"
                    )
                }
            }
        }

        item {
            Spacer(Modifier.height(40.dp))
        }
    }
}

@Preview
@Composable
private fun StudySearchScreenPreview() {
    TogedyTheme {
        StudySearchScreen(
            uiState = StudySearchUiState("", UiState.Loading, UiState.Loading),
            filterState = SearchFilterState(),
            modifier = Modifier,
            onBackClick = {},
            onSearchTermChange = {},
            onStudyItemClick = {},
            onSortOptionClick = {},
            onJoinableClick = {},
            onChallengeClick = {},
        )
    }
}
