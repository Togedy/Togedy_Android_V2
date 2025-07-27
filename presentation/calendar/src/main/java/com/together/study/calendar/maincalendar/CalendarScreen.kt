package com.together.study.calendar.maincalendar

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.together.study.calendar.bottomSheet.ScheduleBottomSheet
import com.together.study.calendar.component.DailyScheduleDialog
import com.together.study.calendar.generateCalendarWeeks
import com.together.study.calendar.maincalendar.component.DayOfWeek
import com.together.study.calendar.maincalendar.component.WeekSchedule
import com.together.study.calendar.maincalendar.state.CalendarUiState
import com.together.study.calendar.model.DDay
import com.together.study.calendar.model.Schedule
import com.together.study.common.ScheduleType
import com.together.study.common.state.UiState
import com.together.study.designsystem.R.drawable.ic_down_chevron_16
import com.together.study.designsystem.component.TogedySearchBar
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.presentation.calendar.R.drawable.ic_volume_16
import com.together.study.presentation.calendar.R.string.calendar_year_month
import com.together.study.util.noRippleClickable
import java.time.LocalDate

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
internal fun CalendarRoute(
    onSearchBoxClick: () -> Unit,
    onCategoryDetailNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    calendarViewModel: CalendarViewModel = hiltViewModel(),
    dailyDialogViewModel: DailyDialogViewModel = hiltViewModel(),
) {
    val uiState by calendarViewModel.calendarUiState.collectAsStateWithLifecycle()

    LaunchedEffect(calendarViewModel.currentDate) {
        // TODO: 스케줄 업데이트
        calendarViewModel.getCalendarInfo()
    }

    CalendarScreen(
        uiState = uiState,
        currentDate = calendarViewModel.currentDate.value,
        onSearchBoxClick = onSearchBoxClick,
        onDateClick = calendarViewModel::updateDailyDialog,
        onAddBtnClick = calendarViewModel::saveNewSchedule,
        onEditBtnClick = calendarViewModel::updateSchedule,
        dailyDialogViewModel = dailyDialogViewModel,
        onCategoryDetailNavigate = onCategoryDetailNavigate,
        modifier = modifier,
    )
}

@Composable
private fun CalendarScreen(
    uiState: CalendarUiState,
    currentDate: LocalDate,
    onSearchBoxClick: () -> Unit,
    onDateClick: (LocalDate) -> Unit,
    onAddBtnClick: (Schedule) -> Unit,
    onEditBtnClick: (Schedule) -> Unit,
    onCategoryDetailNavigate: () -> Unit,
    dailyDialogViewModel: DailyDialogViewModel,
    modifier: Modifier = Modifier,
) {
    when (uiState.isLoaded) {
        is UiState.Loading -> {
        }

        is UiState.Empty -> {
        }

        is UiState.Failure -> {
        }

        is UiState.Success -> {
            with(uiState) {
                CalendarSuccessScreen(
                    notice = (uiState.noticeState as UiState.Success<String>).data,
                    date = currentDate,
                    dDay = (uiState.dDayState as UiState.Success<DDay>).data,
                    schedules = (uiState.scheduleState as UiState.Success<List<Schedule>>).data,
                    onSearchBoxClick = onSearchBoxClick,
                    onDateClick = onDateClick,
                    onYearMonthSectionClick = { /* TODO : 연도,월 선택 다이얼로그 */ },
                    onAddBtnClick = onAddBtnClick,
                    onEditBtnClick = onEditBtnClick,
                    onCategoryDetailNavigate = onCategoryDetailNavigate,
                    dailyDialogViewModel = dailyDialogViewModel,
                    modifier = modifier,
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun CalendarSuccessScreen(
    notice: String,
    date: LocalDate,
    dDay: DDay,
    schedules: List<Schedule>,
    onSearchBoxClick: () -> Unit,
    onDateClick: (LocalDate) -> Unit,
    onYearMonthSectionClick: () -> Unit,
    onAddBtnClick: (Schedule) -> Unit,
    onEditBtnClick: (Schedule) -> Unit,
    onCategoryDetailNavigate: () -> Unit,
    dailyDialogViewModel: DailyDialogViewModel,
    modifier: Modifier = Modifier,
) {
    var weeks = generateCalendarWeeks(date)
    var isDailyDialogVisible by remember { mutableStateOf(false) }
    var selectedScheduleId by remember { mutableStateOf<Long?>(null) }
    var isScheduleBottomSheetVisible by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(color = TogedyTheme.colors.white)
            .padding(horizontal = 16.dp),
    ) {
        stickyHeader {
            NoticeSection(
                notice = notice,
                modifier = Modifier,
            )

            Spacer(Modifier.height(16.dp))

            TogedySearchBar(
                isShowSearch = true,
                onSearchClicked = onSearchBoxClick
            )

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                YearMonthSection(
                    date = date,
                    onClick = onYearMonthSectionClick
                )

                if (dDay.hasDday) DDaySection(dDay = dDay)
            }

            Spacer(Modifier.height(20.dp))

            DayOfWeek()

            Spacer(Modifier.height(8.dp))
        }

        itemsIndexed(weeks) { index, week ->
            WeekSchedule(
                weekDates = week,
                schedules = schedules,
                currentMonth = date.month,
                onDateClick = { date ->
                    onDateClick(date)
                    isDailyDialogVisible = true
                },
            )
        }
    }

    if (isDailyDialogVisible) {
        DailyScheduleDialog(
            date = date,
            dDay = null,
            onDismissRequest = { isDailyDialogVisible = false },
            onScheduleItemClick = { scheduleType, id ->
                if (scheduleType == ScheduleType.USER) {
                    selectedScheduleId = id
                    isScheduleBottomSheetVisible = true
                }
            },
            onAddScheduleClick = { isScheduleBottomSheetVisible = true },
            dailyDialogViewModel = dailyDialogViewModel,
        )
    }

    if (isScheduleBottomSheetVisible) {
        ScheduleBottomSheet(
            onDismissRequest = { isScheduleBottomSheetVisible = false },
            onDoneClick = { schedule ->
                if (schedule.scheduleId?.toInt() == -1) onAddBtnClick(schedule)
                else onEditBtnClick(schedule)
                isScheduleBottomSheetVisible = false
                selectedScheduleId = null
            },
            scheduleId = selectedScheduleId,
            onEditCategoryClick = onCategoryDetailNavigate,
        )
    }
}

@Composable
private fun NoticeSection(
    notice: String,
    modifier: Modifier = Modifier,
) {
    if (notice.isNotEmpty()) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .background(
                    color = TogedyTheme.colors.green,
                    shape = RoundedCornerShape(12.dp),
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(ic_volume_16),
                contentDescription = null,
                tint = TogedyTheme.colors.greenBg,
            )

            Spacer(Modifier.width(4.dp))

            Text(
                text = notice,
                style = TogedyTheme.typography.chip10sb.copy(TogedyTheme.colors.greenBg),
                modifier = Modifier.padding(vertical = 6.dp),
            )
        }
    }
}

@Composable
private fun YearMonthSection(
    date: LocalDate,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .noRippleClickable(onClick),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(calendar_year_month, date.year, date.month.value),
            style = TogedyTheme.typography.title16sb.copy(TogedyTheme.colors.gray700),
        )

        Spacer(Modifier.width(2.dp))

        Icon(
            imageVector = ImageVector.vectorResource(ic_down_chevron_16),
            contentDescription = null,
            tint = TogedyTheme.colors.gray700,
        )
    }
}

@Composable
private fun DDaySection(
    dDay: DDay,
    modifier: Modifier = Modifier,
) {
    val dDayTextStyle = TogedyTheme.typography.body14m.copy(TogedyTheme.colors.gray500)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = dDay.userScheduleName.toString(),
            style = dDayTextStyle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f),
        )

        Spacer(Modifier.width(4.dp))

        Text(
            text = "D-${dDay.remainingDays}",
            style = dDayTextStyle,
        )
    }
}


@Preview
@Composable
private fun CalendarSuccessScreenPreview(modifier: Modifier = Modifier) {
    TogedyTheme {
        CalendarSuccessScreen(
            notice = "알림을 알립니다!",
            date = LocalDate.now(),
            dDay = DDay.mock,
            schedules = emptyList(),
            onSearchBoxClick = {},
            onDateClick = {},
            onYearMonthSectionClick = {},
            onAddBtnClick = {},
            onEditBtnClick = {},
            onCategoryDetailNavigate = {},
            dailyDialogViewModel = DailyDialogViewModel(),
            modifier = modifier,
        )
    }
}
