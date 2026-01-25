package com.together.study.planner.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.together.study.calendar.model.DDay
import com.together.study.common.state.UiState
import com.together.study.designsystem.R.drawable.ic_kebap_menu_circle
import com.together.study.designsystem.R.drawable.ic_left_chevron
import com.together.study.designsystem.R.drawable.ic_right_chevron_green
import com.together.study.designsystem.component.tabbar.PlannerMainTab
import com.together.study.designsystem.component.tabbar.TogedyTabBar
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.planner.component.PlannerDropDownScrim
import com.together.study.planner.component.TimerSection
import com.together.study.planner.component.TodoSection
import com.together.study.planner.main.state.PlannerInfo
import com.together.study.planner.main.state.PlannerSheetState
import com.together.study.planner.type.PlannerSheetType
import com.together.study.util.noRippleClickable
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
internal fun PlannerScreen(
    modifier: Modifier = Modifier,
    onShareNavigate: () -> Unit,
    onTimerNavigate: () -> Unit,
    onEditSubjectNavigate: () -> Unit,
    viewModel: PlannerViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val selectedDate by viewModel.selectedDate.collectAsStateWithLifecycle()
    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()
    val bottomSheetState by viewModel.sheetState.collectAsStateWithLifecycle()

    LaunchedEffect(selectedDate) {
        viewModel.getPlannerInfo(selectedDate)
    }

    when (val plannerInfoState = uiState.plannerInfo) {
        is UiState.Success<*> -> {
            PlannerSuccessScreen(
                plannerInfo = plannerInfoState.data as PlannerInfo,
                selectedTab = selectedTab,
                selectedDate = selectedDate,
                bottomSheetState = bottomSheetState,
                modifier = modifier,
                onTabClick = viewModel::updateSelectedTab,
                onSelectedDateChange = viewModel::updateSelectedDate,
                onShareButtonClick = onShareNavigate,
                onSheetVisibilityChange = viewModel::updateBottomSheetVisibility,
                onPlayButtonClick = onTimerNavigate,
                onEditSubjectClick = onEditSubjectNavigate,
            )
        }

        is UiState.Failure -> {}
        is UiState.Loading -> {}
        is UiState.Empty -> {}
    }
}

@Composable
private fun PlannerSuccessScreen(
    plannerInfo: PlannerInfo,
    selectedTab: PlannerMainTab,
    selectedDate: LocalDate,
    bottomSheetState: PlannerSheetState,
    modifier: Modifier = Modifier,
    onTabClick: (PlannerMainTab) -> Unit,
    onSelectedDateChange: (LocalDate) -> Unit,
    onShareButtonClick: () -> Unit,
    onSheetVisibilityChange: (PlannerSheetType) -> Unit,
    onPlayButtonClick: () -> Unit,
    onEditSubjectClick: () -> Unit,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = PlannerMainTab.entries.indexOf(selectedTab),
        pageCount = { PlannerMainTab.entries.size }
    )

    var showDropdown by remember { mutableStateOf(false) }

    LaunchedEffect(selectedTab) {
        val targetIndex = PlannerMainTab.entries.indexOf(selectedTab)
        if (pagerState.currentPage != targetIndex) {
            coroutineScope.launch {
                pagerState.animateScrollToPage(targetIndex)
            }
        }
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.white)
            .padding(top = 20.dp),
    ) {
        PlannerTopSection(
            selectedDate = selectedDate,
            dDay = plannerInfo.dDay,
            showDropdown = showDropdown,
            onDayBeforeClick = { onSelectedDateChange(selectedDate.minusDays(1)) },
            onDayAfterClick = { onSelectedDateChange(selectedDate.plusDays(1)) },
            onCalendarClick = {
                onSheetVisibilityChange(PlannerSheetType.CALENDAR)
            },
            onKebabMenuClick = { showDropdown = true },
            onDismissRequestDropdown = { showDropdown = false },
            onPlusPlannerSubjectClick = {
                showDropdown = false
                onSheetVisibilityChange(PlannerSheetType.SUBJECT_ADD)
            },
            onEditPlannerSubjectClick = {
                showDropdown = false
                onSheetVisibilityChange(PlannerSheetType.SUBJECT)
            },
            onShareButtonClick = {
                showDropdown = false
                onShareButtonClick()
            },
        )

        Spacer(Modifier.height(16.dp))

        TimerSection(
            context = context,
            timerImageUrl = plannerInfo.timerImageUrl,
            timer = plannerInfo.timer,
            onPlayButtonClick = onPlayButtonClick,
            onImageEditButtonClick = { /* TODO: 갤러리 연결 */ },
        )

        Spacer(Modifier.height(32.dp))

        TogedyTabBar(
            selectedTab = selectedTab,
            onTabChange = onTabClick,
            tabList = PlannerMainTab.entries,
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> PlannerItemsScreen()
                1 -> { /* TODO: 타임테이블 연결 */
                }
                2 -> StatisticsScreen()
            }
        }
    }

    PlannerSheetScreen(
        bottomSheetState = bottomSheetState,
        selectedDate = selectedDate,
        onDismissRequest = onSheetVisibilityChange,
        onEditSubjectClick = onEditSubjectClick,
        onDateChange = onSelectedDateChange,
    )
}

@Composable
private fun PlannerTopSection(
    selectedDate: LocalDate,
    dDay: DDay,
    showDropdown: Boolean,
    onDayBeforeClick: () -> Unit,
    onDayAfterClick: () -> Unit,
    onCalendarClick: () -> Unit,
    onKebabMenuClick: () -> Unit,
    onDismissRequestDropdown: () -> Unit,
    onPlusPlannerSubjectClick: () -> Unit,
    onEditPlannerSubjectClick: () -> Unit,
    onShareButtonClick: () -> Unit,
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.CenterEnd,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(ic_left_chevron),
                    contentDescription = "이전 버튼",
                    tint = TogedyTheme.colors.gray500,
                    modifier = Modifier
                        .size(16.dp)
                        .noRippleClickable(onDayBeforeClick),
                )

                Text(
                    text = "${selectedDate.year}년 ${selectedDate.monthValue}월 ${selectedDate.dayOfMonth}일",
                    style = TogedyTheme.typography.title16sb,
                    color = TogedyTheme.colors.gray800,
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .noRippleClickable(onCalendarClick),
                )

                Icon(
                    imageVector = ImageVector.vectorResource(ic_right_chevron_green),
                    contentDescription = "다음 버튼",
                    tint = TogedyTheme.colors.gray500,
                    modifier = Modifier
                        .size(16.dp)
                        .noRippleClickable(onDayAfterClick),
                )
            }

            Box(
                modifier = Modifier.wrapContentSize(Alignment.TopEnd),
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(ic_kebap_menu_circle),
                    contentDescription = "더보기 버튼",
                    tint = TogedyTheme.colors.gray700,
                    modifier = Modifier.noRippleClickable(onKebabMenuClick),
                )

                PlannerDropDownScrim(
                    expanded = showDropdown,
                    onDismissRequest = onDismissRequestDropdown,
                    onPlusPlannerSubjectClick = onPlusPlannerSubjectClick,
                    onEditPlannerSubjectClick = onEditPlannerSubjectClick,
                    onShareButtonClick = onShareButtonClick,
                )
            }
        }

        if (dDay.hasDday) {
            TodoSection(dDay)
        }
    }
}

@Preview
@Composable
private fun PlannerScreenPreview() {
    TogedyTheme {
        PlannerScreen(
            onShareNavigate = {},
            onTimerNavigate = {},
            onEditSubjectNavigate = {},
        )
    }
}