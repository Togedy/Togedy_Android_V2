package com.together.study.planner.main

import android.content.Context
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.together.study.calendar.model.DDay
import com.together.study.designsystem.R.drawable.ic_add_image
import com.together.study.designsystem.R.drawable.ic_kebap_menu_circle
import com.together.study.designsystem.R.drawable.ic_left_chevron
import com.together.study.designsystem.R.drawable.ic_play_button
import com.together.study.designsystem.R.drawable.ic_right_chevron_green
import com.together.study.designsystem.component.tabbar.PlannerMainTab
import com.together.study.designsystem.component.tabbar.TogedyTabBar
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.planner.component.PlannerDropDownScrim
import com.together.study.util.noRippleClickable
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun PlannerScreen(
    modifier: Modifier = Modifier,
    onShareNavigate: () -> Unit,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedTab by remember { mutableStateOf(PlannerMainTab.PLANNER) }
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

    LaunchedEffect(pagerState.currentPage) {
        val currentTab = PlannerMainTab.entries[pagerState.currentPage]
        if (selectedTab != currentTab) {
            selectedTab = currentTab
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
            dDay = DDay(true, "수능", -100),
            showDropdown = showDropdown,
            onDayBeforeClick = { selectedDate = selectedDate.minusDays(1) },
            onDayAfterClick = { selectedDate = selectedDate.plusDays(1) },
            onCalendarClick = { },
            onKebabMenuClick = { showDropdown = true },
            onDismissRequestDropdown = { showDropdown = false },
            onPlusPlannerSubjectClick = {
                showDropdown = false
            },
            onEditPlannerSubjectClick = {
                showDropdown = false
            },
            onShareButtonClick = {
                showDropdown = false
                onShareNavigate()
            },
        )

        Spacer(Modifier.height(16.dp))

        TimerSection(
            context = context,
            timerImageUrl = "",
            timer = "00:00:00",
            onPlayButtonClick = { },
            onImageEditButtonClick = { },
        )

        Spacer(Modifier.height(32.dp))

        TogedyTabBar(
            selectedTab = selectedTab,
            onTabChange = { selectedTab = it },
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
            val dDayText = when {
                dDay.remainingDays == 0 -> "D-DAY"
                dDay.remainingDays!! < 0 -> "D${dDay.remainingDays}"
                else -> "D+${dDay.remainingDays}"
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "${dDay.userScheduleName}",
                    style = TogedyTheme.typography.body10m,
                    color = TogedyTheme.colors.green,
                    modifier = Modifier.padding(horizontal = 3.dp),
                )

                Text(
                    text = dDayText,
                    style = TogedyTheme.typography.body10m,
                    color = TogedyTheme.colors.gray700,
                    modifier = Modifier.padding(horizontal = 3.dp),
                )
            }
        }
    }
}

@Composable
private fun TimerSection(
    context: Context,
    timerImageUrl: String,
    timer: String,
    modifier: Modifier = Modifier,
    onPlayButtonClick: () -> Unit,
    onImageEditButtonClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.TopCenter,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 20.dp)
                .clip(RoundedCornerShape(16.dp)),
        ) {
            AsyncImage(
                model = ImageRequest
                    .Builder(context)
                    .data(timerImageUrl)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(
                    color = TogedyTheme.colors.gray500.copy(alpha = 0.5f),
                    blendMode = BlendMode.Darken
                ),
                modifier = Modifier.height(114.dp),
                error = ColorPainter(Color.White),
                placeholder = ColorPainter(Color.White),
                fallback = ColorPainter(Color.White),
            )

            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(
                        text = "Study Time",
                        style = TogedyTheme.typography.body12m,
                        color = TogedyTheme.colors.white,
                    )

                    Spacer(Modifier.height(16.dp))

                    Text(
                        text = timer,
                        style = TogedyTheme.typography.time40l,
                        color = TogedyTheme.colors.white,
                    )
                }

                Spacer(Modifier.weight(1f))

                Icon(
                    imageVector = ImageVector.vectorResource(ic_play_button),
                    contentDescription = "타이머버튼",
                    tint = Color.Unspecified,
                    modifier = Modifier.noRippleClickable(onPlayButtonClick)
                )
            }
        }

        Icon(
            imageVector = ImageVector.vectorResource(ic_add_image),
            contentDescription = "이미지 추가 버튼",
            tint = TogedyTheme.colors.gray500,
            modifier = Modifier
                .shadow(4.dp, RoundedCornerShape(50.dp))
                .background(TogedyTheme.colors.white, RoundedCornerShape(16.dp))
                .padding(8.dp)
                .noRippleClickable(onImageEditButtonClick),
        )
    }
}

@Preview
@Composable
private fun PlannerScreenPreview() {
    TogedyTheme {
        PlannerScreen(
            onShareNavigate = {},
        )
    }
}