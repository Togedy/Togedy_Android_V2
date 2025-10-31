package com.together.study.studydetail.detailmain

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.together.study.common.state.UiState
import com.together.study.common.type.study.StudyType
import com.together.study.designsystem.R.drawable.ic_left_chevron
import com.together.study.designsystem.R.drawable.ic_right_chevron_green
import com.together.study.designsystem.R.drawable.ic_settings_24
import com.together.study.designsystem.R.drawable.ic_share_20
import com.together.study.designsystem.R.drawable.img_study_background
import com.together.study.designsystem.component.button.TogedyButton
import com.together.study.designsystem.component.tabbar.StudyDetailTab
import com.together.study.designsystem.component.tabbar.TogedyTabBar
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.study.type.StudyRole
import com.together.study.studydetail.detailmain.component.AttendanceItem
import com.together.study.studydetail.detailmain.component.DailyCompletionBar
import com.together.study.studydetail.detailmain.component.StudyDetailDialogScreen
import com.together.study.studydetail.detailmain.component.StudyInfoSection
import com.together.study.studydetail.detailmain.component.StudyMemberItem
import com.together.study.studydetail.detailmain.state.StudyDetailDialogState
import com.together.study.studydetail.detailmain.state.StudyDetailUiState
import com.together.study.studydetail.detailmain.type.StudyDetailDialogType
import com.together.study.util.noRippleClickable
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields

@Composable
internal fun StudyDetailRoute(
    onBackClick: () -> Unit,
    onSettingsNavigate: (Long, StudyRole) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: StudyDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.studyDetailUiState.collectAsStateWithLifecycle()
    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()
    val selectedDate by viewModel.selectedDate.collectAsStateWithLifecycle()
    val dialogState by viewModel.dialogState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getStudyDetailInfo()
    }

    StudyDetailScreen(
        studyId = viewModel.studyId,
        uiState = uiState,
        selectedTab = selectedTab,
        selectedDate = selectedDate,
        dialogState = dialogState,
        modifier = modifier,
        onBackClick = onBackClick,
        onShareButtonClick = {},
        onSettingsButtonClick = onSettingsNavigate,
        onTabChange = viewModel::updateSelectedTab,
        onUserClick = { viewModel.updateDialogState(StudyDetailDialogType.USER) },
        onPreviousWeekClick = { viewModel.updateSelectedDate("이전") },
        onNextWeekClick = { viewModel.updateSelectedDate("다음") },
        onDialogStateChange = viewModel::updateDialogState,
        onJoinStudyClick = viewModel::joinStudy,
    )
}

@Composable
private fun StudyDetailScreen(
    studyId: Long,
    uiState: StudyDetailUiState,
    selectedTab: StudyDetailTab,
    selectedDate: LocalDate,
    dialogState: StudyDetailDialogState,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onShareButtonClick: () -> Unit,
    onSettingsButtonClick: (Long, StudyRole) -> Unit,
    onTabChange: (StudyDetailTab) -> Unit,
    onUserClick: () -> Unit,
    onPreviousWeekClick: () -> Unit,
    onNextWeekClick: () -> Unit,
    onDialogStateChange: (StudyDetailDialogType) -> Unit,
    onJoinStudyClick: () -> Unit,
) {
    when (uiState.isLoaded) {
        is UiState.Empty -> {}
        is UiState.Failure -> {}
        is UiState.Loading -> {}
        is UiState.Success -> {
            StudyDetailSuccessScreen(
                studyId = studyId,
                uiState = uiState,
                selectedTab = selectedTab,
                selectedDate = selectedDate,
                dialogState = dialogState,
                modifier = modifier,
                onBackClick = onBackClick,
                onShareButtonClick = onShareButtonClick,
                onSettingsButtonClick = onSettingsButtonClick,
                onTabChange = onTabChange,
                onUserClick = onUserClick,
                onPreviousWeekClick = onPreviousWeekClick,
                onNextWeekClick = onNextWeekClick,
                onDialogStateChange = onDialogStateChange,
                onJoinStudyClick = onJoinStudyClick,
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun StudyDetailSuccessScreen(
    studyId: Long,
    uiState: StudyDetailUiState,
    selectedTab: StudyDetailTab,
    selectedDate: LocalDate,
    dialogState: StudyDetailDialogState,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onShareButtonClick: () -> Unit,
    onSettingsButtonClick: (Long, StudyRole) -> Unit,
    onTabChange: (StudyDetailTab) -> Unit,
    onUserClick: () -> Unit,
    onPreviousWeekClick: () -> Unit,
    onNextWeekClick: () -> Unit,
    onDialogStateChange: (StudyDetailDialogType) -> Unit,
    onJoinStudyClick: () -> Unit,
) {
    val context = LocalContext.current
    val studyInfo = (uiState.studyInfoState as UiState.Success).data
    val members = (uiState.membersState as UiState.Success).data
    val attendance = (uiState.attendanceState as UiState.Success).data

    val isCurrentWeek = isCurrentWeek(selectedDate)
    var selectedUserId by remember { mutableLongStateOf(0) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.white),
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
            ) {
                AsyncImage(
                    model = ImageRequest
                        .Builder(context)
                        .data(studyInfo.studyImageUrl)
                        .placeholder(img_study_background)
                        .error(img_study_background)
                        .fallback(img_study_background)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .drawWithContent {
                            drawContent()
                            val gradientHeight = size.height * 0.9f

                            drawRect(
                                brush = Brush.verticalGradient(
                                    colors = listOf(Color.Black, Color.Transparent),
                                    startY = 0f,
                                    endY = gradientHeight,
                                ),
                                topLeft = Offset.Zero,
                                size = Size(width = size.width, height = gradientHeight),
                            )
                        },
                )
            }

            StudyInfoSection(
                studyTag = studyInfo.studyTag,
                studyName = studyInfo.studyName,
                studyDescription = studyInfo.studyDescription,
                studyTier = studyInfo.studyTag,
                studyMemberCount = studyInfo.studyMemberCount,
                studyMemberLimit = studyInfo.studyMemberLimit,
                modifier = Modifier,
            )

            if (studyInfo.isJoined && studyInfo.completedMemberCount != 0 && studyInfo.studyType == StudyType.CHALLENGE.name) {
                DailyCompletionBar(
                    studyInfo.completedMemberCount!!,
                    studyInfo.studyMemberCount,
                )
            } else {
                HorizontalDivider(thickness = 8.dp, color = TogedyTheme.colors.gray50)
            }
        }

        stickyHeader {
            TogedyTabBar(
                tabList = StudyDetailTab.entries,
                selectedTab = selectedTab,
                onTabChange = onTabChange,
                modifier = Modifier.background(TogedyTheme.colors.white),
            )
        }

        when (selectedTab) {
            StudyDetailTab.MEMBER -> {
                val chunkedList = members.chunked(4)

                item {
                    Spacer(modifier = Modifier.height(10.dp))
                }

                itemsIndexed(chunkedList) { _, users ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        users.forEach { user ->
                            StudyMemberItem(
                                context = context,
                                user = user,
                                modifier = Modifier.weight(1f),
                                onItemClick = {
                                    selectedUserId = user.userId
                                    if (studyInfo.isJoined) onUserClick()
                                },
                            )
                        }

                        if (users.size < 4) {
                            repeat(4 - users.size) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }

            StudyDetailTab.DAILY_CHECK -> {
                item {
                    Row(
                        modifier = Modifier
                            .padding(start = 26.dp, top = 16.dp, end = 26.dp, bottom = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(ic_left_chevron),
                            contentDescription = null,
                            tint = TogedyTheme.colors.gray300,
                            modifier = Modifier.noRippleClickable(onPreviousWeekClick),
                        )

                        Spacer(Modifier.weight(1f))

                        with(selectedDate) {
                            Text(
                                text = "${year}년 ${monthValue}월 ${getYearMonthWeek(selectedDate)}주차",
                                style = TogedyTheme.typography.body14b,
                                color = TogedyTheme.colors.gray700,
                            )
                        }

                        Spacer(Modifier.weight(1f))

                        Icon(
                            imageVector = ImageVector.vectorResource(ic_right_chevron_green),
                            contentDescription = null,
                            tint = if (!isCurrentWeek) TogedyTheme.colors.gray300 else TogedyTheme.colors.gray100,
                            modifier = Modifier.noRippleClickable {
                                if (!isCurrentWeek) onNextWeekClick()
                            },
                        )
                    }
                }

                itemsIndexed(attendance) { index, attendance ->
                    Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
                        AttendanceItem(
                            ranking = index + 1,
                            attendance = attendance,
                            selectedDate = selectedDate,
                            isCurrentWeek = isCurrentWeek,
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(60.dp))
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(TogedyTheme.colors.black)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(ic_left_chevron),
                contentDescription = "뒤로 가기 버튼",
                tint = TogedyTheme.colors.white,
                modifier = Modifier
                    .size(24.dp)
                    .noRippleClickable(onBackClick),
            )

            Spacer(Modifier.weight(1f))

            Icon(
                imageVector = ImageVector.vectorResource(ic_share_20),
                contentDescription = "공유 버튼",
                tint = TogedyTheme.colors.white,
                modifier = Modifier
                    .size(20.dp)
                    .noRippleClickable(onShareButtonClick),
            )

            if (studyInfo.isJoined) {
                Spacer(Modifier.width(8.dp))

                Icon(
                    imageVector = ImageVector.vectorResource(ic_settings_24),
                    contentDescription = "설정 버튼",
                    tint = TogedyTheme.colors.white,
                    modifier = Modifier
                        .size(24.dp)
                        .noRippleClickable {
                            val role =
                                if (studyInfo.isStudyLeader) StudyRole.LEADER else StudyRole.MEMBER
                            onSettingsButtonClick(studyId, role)
                        },
                )
            }
        }

        if (studyInfo.isJoined) {
            //TODO: 스탑워치 화면이동 버튼으로 변경
        } else {
            Box(
                modifier = Modifier
                    .background(color = TogedyTheme.colors.gray300)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            ) {
                TogedyButton(
                    text = "스터디 가입하기",
                    onClick = { onDialogStateChange(StudyDetailDialogType.JOIN) },
                )
            }
        }
    }

    StudyDetailDialogScreen(
        studyId = studyId,
        userId = selectedUserId,
        studyInfo = studyInfo,
        dialogState = dialogState,
        onDismissRequest = onDialogStateChange,
        onJoinStudyClick = {
            onJoinStudyClick()
            onDialogStateChange(StudyDetailDialogType.JOIN)
        }
    )
}

private fun getYearMonthWeek(selectedDate: LocalDate): Int {
    val weekFields = WeekFields.of(DayOfWeek.MONDAY, 1)
    val weekOfMonth = selectedDate.get(weekFields.weekOfMonth())

    return weekOfMonth
}

private fun isCurrentWeek(targetDate: LocalDate): Boolean {
    val startOfWeek = targetDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
    val endOfWeek = targetDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
    val today = LocalDate.now()

    return today in startOfWeek..endOfWeek
}
