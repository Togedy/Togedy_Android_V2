package com.together.study.studydetail.detailmain

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.together.study.common.state.UiState
import com.together.study.designsystem.R.drawable.ic_lock
import com.together.study.designsystem.R.drawable.img_character_sleeping
import com.together.study.designsystem.R.drawable.img_study_background
import com.together.study.designsystem.component.button.TogedyToggleButton
import com.together.study.designsystem.component.tabbar.StudyMemberTab
import com.together.study.designsystem.component.tabbar.TogedyTabBar
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.study.model.StudyMemberPlanner
import com.together.study.study.model.StudyMemberProfile
import com.together.study.study.model.StudyMemberTimeBlocks
import com.together.study.studydetail.detailmain.component.StudyMonthlyColorBlock
import com.together.study.util.noRippleClickable
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun UserInfoBottomSheet(
    studyId: Long,
    userId: Long,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onPlannerEditClick: () -> Unit,
    viewModel: UserInfoViewModel = hiltViewModel(),
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var selectedTab by remember { mutableStateOf(StudyMemberTab.STUDY_TIME) }
    val context = LocalContext.current

    val isPlannerVisible by viewModel.isPlannerVisibleToggle.collectAsStateWithLifecycle()
    val memberUiState by viewModel.memberUiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getStudyMemberInfo(studyId, userId)
    }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        modifier = modifier.fillMaxWidth(),
        containerColor = TogedyTheme.colors.white,
    ) {
        when (memberUiState.isLoaded) {
            is UiState.Empty -> {}
            is UiState.Failure -> {}
            is UiState.Loading -> {}
            is UiState.Success<*> -> {
                val user = (memberUiState.profileState as UiState.Success).data
                val dailyPlanner = (memberUiState.plannerState as UiState.Success).data

                UserInfoSuccessScreen(
                    context = context,
                    selectedTab = selectedTab,
                    isPlannerVisible = isPlannerVisible,
                    user = user,
                    studyTimeBlocks = (memberUiState.timeBlocksState as UiState.Success<StudyMemberTimeBlocks>).data,
                    dailyPlanner = dailyPlanner,
                    modifier = modifier,
                    onTabChange = { selectedTab = it },
                    onPlannerVisibleToggleClick = {
                        viewModel.onPlannerVisibleToggleClicked(
                            isPlannerVisible
                        )
                    },
                    onPlannerEditClick = onPlannerEditClick,
                )
            }
        }
    }
}

@Composable
private fun UserInfoSuccessScreen(
    context: Context,
    selectedTab: StudyMemberTab,
    isPlannerVisible: Boolean,
    user: StudyMemberProfile,
    studyTimeBlocks: StudyMemberTimeBlocks,
    dailyPlanner: StudyMemberPlanner,
    modifier: Modifier = Modifier,
    onTabChange: (StudyMemberTab) -> Unit,
    onPlannerVisibleToggleClick: () -> Unit,
    onPlannerEditClick: () -> Unit,
) {
    val totalStudyTime by remember { mutableStateOf(changeToTotalStudyTime(user.totalStudyTime)) }
    val attendanceStreak by remember { mutableStateOf(checkMaxValue(user.attendanceStreak)) }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = ImageRequest
                .Builder(context)
                .data(user.userProfileImageUrl)
                .placeholder(img_study_background)
                .error(img_study_background)
                .fallback(img_study_background)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(50.dp)),
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = user.userName,
            style = TogedyTheme.typography.title18b,
            color = TogedyTheme.colors.gray900,
        )

        Spacer(Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            UserRecordBlock(
                record = totalStudyTime,
                type = "누적 공부시간"
            )
            UserRecordBlock(
                record = attendanceStreak,
                unit = "일",
                type = "연속 출석"
            )
            UserRecordBlock(
                record = user.elapsedDays.toString(),
                unit = "DAY",
                type = "스터디 시작일"
            )
        }

        Spacer(Modifier.height(20.dp))

        HorizontalDivider(color = TogedyTheme.colors.gray100)

        TogedyTabBar(
            tabList = StudyMemberTab.entries,
            selectedTab = selectedTab,
            onTabChange = onTabChange,
        )

        when (selectedTab) {
            StudyMemberTab.STUDY_TIME -> {
                val currentDate = LocalDate.now()
                val currentYear = currentDate.year
                val currentMonth = currentDate.monthValue

                StudyTimeTitleSection()

                if (studyTimeBlocks.studyTimeCount != 0) {
                    CurrentMonthlyStudyCount(
                        userName = user.userName,
                        count = studyTimeBlocks.studyTimeCount
                    )
                }

                if (studyTimeBlocks.monthlyStudyTimeList.isEmpty()) {
                    val monthsToShow = listOf(
                        LocalDate.of(currentYear, currentMonth, 1),
                        LocalDate.of(currentYear, currentMonth, 1).minusMonths(1)
                    )

                    LazyRow(
                        modifier = Modifier,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        reverseLayout = true,
                    ) {
                        item { Spacer(Modifier.width(6.dp)) }

                        items(monthsToShow) { date ->
                            val year = date.year
                            val month = date.monthValue
                            val daysInMonth = date.lengthOfMonth()

                            val emptyStudyTimeList = List(daysInMonth) { 1 }

                            StudyMonthlyColorBlock(
                                year = year,
                                month = month,
                                studyTimeList = emptyStudyTimeList,
                            )
                        }

                        item { Spacer(Modifier.width(6.dp)) }
                    }
                } else {
                    LazyRow(
                        modifier = Modifier,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        reverseLayout = true,
                    ) {
                        item { Spacer(Modifier.width(6.dp)) }

                        itemsIndexed(studyTimeBlocks.monthlyStudyTimeList.reversed()) { index, item ->
                            StudyMonthlyColorBlock(
                                year = item.year,
                                month = item.month,
                                studyTimeList = item.studyTimeList,
                            )
                        }

                        item { Spacer(Modifier.width(6.dp)) }
                    }
                }
            }

            StudyMemberTab.PLANNER -> {
                with(dailyPlanner) {
                    PlannerTitleSection(
                        isMyPlanner = isMyPlanner,
                        isPlannerVisible = isPlannerVisible,
                        completedPlanCount = completedPlanCount,
                        totalPlanCount = totalPlanCount,
                    )

                    if (isMyPlanner) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .background(
                                    TogedyTheme.colors.gray100,
                                    RoundedCornerShape(8.dp)
                                ),
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp)
                                    .background(
                                        TogedyTheme.colors.gray100,
                                        RoundedCornerShape(8.dp)
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(
                                    text = "다른 멤버에게 공개할래요",
                                    style = TogedyTheme.typography.chip10sb,
                                    color = TogedyTheme.colors.gray900,
                                )

                                TogedyToggleButton(
                                    isToggleOn = isPlannerVisible,
                                    onToggleClick = onPlannerVisibleToggleClick,
                                )
                            }
                        }
                    }

                    when {
                        dailyPlanner.dailyPlanner != null && dailyPlanner.isPlannerVisible -> {
                            UserDailyPlanner(plans = dailyPlanner.dailyPlanner!!)
                        }

                        !isMyPlanner && !dailyPlanner.isPlannerVisible -> {
                            UnOpenedPlanner()
                        }

                        else -> EmptyDailyPlanner()
                    }

                    if (isMyPlanner) {
                        Spacer(Modifier.height(10.dp))

                        Text(
                            text = "플래너 수정하기",
                            style = TogedyTheme.typography.body14m,
                            color = TogedyTheme.colors.white,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .background(
                                    color = TogedyTheme.colors.black,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .noRippleClickable(onPlannerEditClick),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CurrentMonthlyStudyCount(
    userName: String,
    count: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 12.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(
                    color = TogedyTheme.colors.greenBg,
                    shape = RoundedCornerShape(8.dp),
                )
                .clip(RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp, vertical = 10.dp),
            contentAlignment = Alignment.TopEnd,
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = TogedyTheme.colors.gray800)) {
                        append("${userName}님")
                    }
                    withStyle(SpanStyle(color = TogedyTheme.colors.gray600)) {
                        append("은 이번 달 총 ")
                    }
                    withStyle(SpanStyle(color = TogedyTheme.colors.green)) {
                        append("${count}일")
                    }
                    withStyle(SpanStyle(color = TogedyTheme.colors.gray600)) {
                        append(" 공부했어요 \uD83D\uDD25")
                    }
                },
                style = TogedyTheme.typography.body13b,
                modifier = Modifier.align(Alignment.CenterStart),
            )

            Image(
                painter = painterResource(img_character_sleeping),
                contentDescription = null,
                modifier = Modifier
                    .width(57.dp)
                    .aspectRatio(1f)
                    .alpha(0.2f),
            )
        }
    }
}

@Composable
private fun StudyTimeTitleSection(modifier: Modifier = Modifier) {
    val stackColorList = listOf(
        TogedyTheme.colors.gray200,
        TogedyTheme.colors.green500,
        TogedyTheme.colors.green600,
        TogedyTheme.colors.green800,
        TogedyTheme.colors.green,
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Study Tracker",
            style = TogedyTheme.typography.body14b,
            color = TogedyTheme.colors.black
        )

        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "5 stack",
                style = TogedyTheme.typography.body10m,
                color = TogedyTheme.colors.gray400,
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(1.dp),
            ) {
                repeat(5) { count ->
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                color = stackColorList[count],
                                shape = RoundedCornerShape(2.dp)
                            ),
                    )
                }
            }
        }
    }
}

@Composable
private fun PlannerTitleSection(
    isMyPlanner: Boolean,
    isPlannerVisible: Boolean,
    completedPlanCount: Int?,
    totalPlanCount: Int?,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Daily planner",
            style = TogedyTheme.typography.body14b,
            color = TogedyTheme.colors.black
        )

        if (isMyPlanner || isPlannerVisible) {
            Text(
                text = buildAnnotatedString {
                    append("오늘의 할 일 ")
                    withStyle(SpanStyle(color = TogedyTheme.colors.green)) {
                        append("${completedPlanCount ?: 0}")
                    }
                    append("/${totalPlanCount ?: 0}")
                },
                style = TogedyTheme.typography.body12m,
                color = TogedyTheme.colors.gray500,
            )
        }
    }
}


@Composable
private fun UserDailyPlanner(
    plans: List<StudyMemberPlanner.DailyPlanner>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .height(300.dp),
    ) {
        itemsIndexed(plans) { index, item ->
            Column(
                modifier = Modifier.padding(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = item.studyCategoryName,
                    style = TogedyTheme.typography.body14b,
                    color = TogedyTheme.colors.gray900,
                )

                HorizontalDivider(color = TogedyTheme.colors.gray50)

                item.planList.forEach { plan ->
                    val status =
                        if (plan.planStatus == "완료") TextDecoration.LineThrough
                        else TextDecoration.None
                    val color =
                        if (plan.planStatus == "완료") TogedyTheme.colors.gray500
                        else TogedyTheme.colors.gray900

                    Text(
                        text = plan.planName,
                        style = TogedyTheme.typography.body13m,
                        color = color,
                        textDecoration = status
                    )

                    HorizontalDivider(color = TogedyTheme.colors.gray50)
                }
            }
        }
    }
}

@Composable
private fun EmptyDailyPlanner(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.height(200.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "오늘의 일정이 없습니다.",
            style = TogedyTheme.typography.body14m,
            color = TogedyTheme.colors.gray500,
        )
    }
}

@Composable
private fun UnOpenedPlanner(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.height(200.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(ic_lock),
            contentDescription = null,
            tint = Color.Unspecified,
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = "플래너가 비공개 상태예요",
            style = TogedyTheme.typography.body14b,
            color = TogedyTheme.colors.gray700,
        )

        Text(
            text = "이 멤버가 개인 플래너를\n다른 사람들에게 공개하지 않기로 했어요",
            style = TogedyTheme.typography.body12m,
            color = TogedyTheme.colors.gray500,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun UserRecordBlock(
    record: String,
    type: String,
    unit: String = "",
    modifier: Modifier = Modifier,
) {
    val recordColor =
        if (type == "스터디 시작일") TogedyTheme.colors.green
        else TogedyTheme.colors.gray800

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(color = recordColor)) {
                    append(record)
                }
                withStyle(SpanStyle(color = TogedyTheme.colors.gray800)) {
                    append(unit)
                }
            },
            style = TogedyTheme.typography.body14b,
        )

        Text(
            text = type,
            style = TogedyTheme.typography.body12m,
            color = TogedyTheme.colors.gray500,
        )
    }
}

private fun changeToTotalStudyTime(time: String): String {
    val times = time.split(":").mapNotNull { it.toIntOrNull() }
    if (times.size < 2) return ""

    val hourValue = times[0]
    val minuteValue = times[1]

    val hour = when {
        hourValue == 0 -> ""
        hourValue > 999 -> "999+h"
        else -> "${hourValue}h"
    }
    val minutes = when {
        minuteValue == 0 || hourValue > 999 -> ""
        minuteValue > 999 -> "999+m"
        else -> "${minuteValue}m"
    }

    return if (hour.isBlank() && minutes.isBlank()) "0h"
    else if (hour.isNotEmpty() && minutes.isNotEmpty()) "$hour $minutes"
    else "$hour$minutes"
}

private fun checkMaxValue(value: Int): String {
    return if (value > 999) "999+" else value.toString()
}

@Preview
@Composable
private fun UserInfoBottomSheetPreview() {
    TogedyTheme {
        UserInfoBottomSheet(
            studyId = 1,
            userId = 1,
            onDismissRequest = {},
            onPlannerEditClick = {},
        )
    }
}
