package com.together.study.study.main

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.together.study.common.state.UiState
import com.together.study.common.type.study.StudySortingType
import com.together.study.designsystem.R.drawable.ic_search_24
import com.together.study.designsystem.component.tabbar.StudyMainTab
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.study.component.SortBottomSheet
import com.together.study.study.component.StudyItem
import com.together.study.study.main.component.EmptyMyStudy
import com.together.study.study.main.component.ExploreFilterSection
import com.together.study.study.main.component.MainTabSection
import com.together.study.study.main.component.MyStudyItem
import com.together.study.study.main.component.TimerSection
import com.together.study.study.main.state.MyStudyInfo
import com.together.study.study.main.state.StudyMainUiState
import com.together.study.study.type.StudyTagType
import com.together.study.util.noRippleClickable
import kotlinx.coroutines.launch

@Composable
internal fun StudyMainRoute(
    onStudySearchNavigate: () -> Unit,
    onStudyDetailNavigate: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: StudyMainViewModel = hiltViewModel(),
) {
    val uiState by viewModel.studyMainUiState.collectAsStateWithLifecycle()
    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getStudyMainInfo()
    }

    StudyMainScreen(
        uiState = uiState,
        selectedTab = selectedTab,
        modifier = modifier,
        onTabClick = viewModel::updateSelectedTab,
        onSearchButtonClick = onStudySearchNavigate,
        onStudyItemClick = onStudyDetailNavigate,
        onTagFilterClick = viewModel::updateTagFilters,
        onSortOptionClick = viewModel::updateSortOption,
        onJoinableClick = viewModel::updateIsJoinable,
        onChallengeClick = viewModel::updateIsChallenge,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun StudyMainScreen(
    uiState: StudyMainUiState,
    selectedTab: StudyMainTab,
    modifier: Modifier = Modifier,
    onTabClick: (StudyMainTab) -> Unit,
    onSearchButtonClick: () -> Unit,
    onStudyItemClick: (Long) -> Unit,
    onTagFilterClick: (StudyTagType) -> Unit,
    onSortOptionClick: (StudySortingType) -> Unit,
    onJoinableClick: () -> Unit,
    onChallengeClick: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    var isSortBottomSheetVisible by remember { mutableStateOf(false) }
    val mainColor =
        if (selectedTab == StudyMainTab.MAIN) TogedyTheme.colors.white
        else TogedyTheme.colors.black
    val subColor =
        if (selectedTab == StudyMainTab.MAIN) TogedyTheme.colors.gray700
        else TogedyTheme.colors.gray500
    val backgroundColor =
        if (selectedTab == StudyMainTab.MAIN) TogedyTheme.colors.black
        else TogedyTheme.colors.gray50
    val topSectionModifier = Modifier
        .fillMaxWidth()
        .background(backgroundColor)


    val pagerState = rememberPagerState(
        initialPage = StudyMainTab.entries.indexOf(selectedTab),
        pageCount = { StudyMainTab.entries.size }
    )

    LaunchedEffect(selectedTab) {
        val targetIndex = StudyMainTab.entries.indexOf(selectedTab)
        if (pagerState.currentPage != targetIndex) {
            coroutineScope.launch {
                pagerState.animateScrollToPage(targetIndex)
            }
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        val currentTab = StudyMainTab.entries[pagerState.currentPage]
        if (selectedTab != currentTab) onTabClick(currentTab)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.gray200),
    ) {
        Box(modifier = topSectionModifier) {
            Column {
                Spacer(Modifier.height(22.dp))

                TitleSection(
                    mainColor = mainColor,
                    onSearchButtonClick = onSearchButtonClick,
                    onCreateButtonClick = {},
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(Modifier.height(16.dp))

                MainTabSection(
                    selectedTab = selectedTab,
                    mainColor = mainColor,
                    subColor = subColor,
                    backgroundColor = backgroundColor,
                    onTabClick = onTabClick,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
        ) { page ->
            when (StudyMainTab.entries[page]) {
                StudyMainTab.MAIN -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        when (uiState.isLoaded) {
                            is UiState.Empty -> {}
                            is UiState.Failure -> {}
                            is UiState.Loading -> {}
                            is UiState.Success -> {
                                with((uiState.myStudyState as UiState.Success<MyStudyInfo>).data) {
                                    item { TimerSection(studyMainTimerInfo) }

                                    item {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(TogedyTheme.colors.gray200)
                                                .padding(16.dp),
                                        ) {
                                            Text(
                                                text = "내 스터디",
                                                style = TogedyTheme.typography.title16sb,
                                                color = TogedyTheme.colors.gray800,
                                            )
                                        }
                                    }

                                    if (studyList.isEmpty()) {
                                        item {
                                            EmptyMyStudy(
                                                onJoinButtonClick = { onTabClick(StudyMainTab.EXPLORE) },
                                            )
                                        }
                                    } else {
                                        items(studyList) { study ->
                                            Box(
                                                modifier = Modifier.padding(16.dp, 4.dp),
                                            ) {
                                                MyStudyItem(
                                                    study = study,
                                                    onItemClick = { onStudyItemClick(study.studyId) },
                                                )
                                            }
                                        }
                                    }

                                    item { Spacer(Modifier.height(20.dp)) }
                                }
                            }
                        }
                    }
                }

                StudyMainTab.EXPLORE -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(TogedyTheme.colors.gray100)
                    ) {
                        stickyHeader {
                            with(uiState.exploreFilterState) {
                                ExploreFilterSection(
                                    selectedFilter = tagFilters,
                                    selectedSortingType = sortOption,
                                    isJoinable = isJoinable,
                                    isChallenge = isChallenge,
                                    modifier = Modifier.background(TogedyTheme.colors.gray100),
                                    onFilterClick = onTagFilterClick,
                                    onSortingClick = { isSortBottomSheetVisible = true },
                                    onJoinableClick = onJoinableClick,
                                    onChallengeClick = onChallengeClick,
                                )
                            }
                        }

                        itemsIndexed((uiState.exploreStudyState as UiState.Success).data) { _, study ->
                            Box(
                                modifier = Modifier
                                    .background(TogedyTheme.colors.gray100)
                                    .padding(horizontal = 16.dp, vertical = 4.dp),
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

                StudyMainTab.BADGE -> {
                    /* TODO() : 추후 스프린트 */
                }
            }
        }
    }

    if (isSortBottomSheetVisible) {
        SortBottomSheet(
            selectedSortOption = uiState.exploreFilterState.sortOption,
            onDismissRequest = { isSortBottomSheetVisible = false },
            onSortOptionClick = {
                onSortOptionClick(it)
                isSortBottomSheetVisible = false
            },
        )
    }
}

@Composable
private fun TitleSection(
    mainColor: Color,
    onSearchButtonClick: () -> Unit,
    onCreateButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.padding(start = 22.dp, end = 16.dp),
    ) {
        Text(text = "스터디", style = TogedyTheme.typography.title18b, color = mainColor)

        Spacer(Modifier.weight(1f))

        Icon(
            imageVector = ImageVector.vectorResource(ic_search_24),
            contentDescription = "검색 버튼",
            tint = mainColor,
            modifier = Modifier.noRippleClickable(onSearchButtonClick),
        )

        Spacer(Modifier.width(16.dp))

        Icon(
            imageVector = ImageVector.vectorResource(ic_search_24),
            contentDescription = "스터디 생성 버튼",
            tint = mainColor,
            modifier = Modifier.noRippleClickable(onCreateButtonClick),
        )
    }
}
