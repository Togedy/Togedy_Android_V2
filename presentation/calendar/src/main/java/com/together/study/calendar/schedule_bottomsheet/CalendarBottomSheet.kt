package com.together.study.calendar.schedule_bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.calendar.maincalendar.component.DayOfWeek
import com.together.study.designsystem.R.drawable.ic_left_chevron_green
import com.together.study.designsystem.R.drawable.ic_replay
import com.together.study.designsystem.R.drawable.ic_right_chevron_green
import com.together.study.designsystem.component.button.TogedyToggleButton
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable
import java.time.LocalDate
import java.time.YearMonth
import kotlin.math.ceil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CalendarBottomSheet(
    startDate: LocalDate,
    onDismissRequest: () -> Unit,
    onDoneClick: (LocalDate, LocalDate?) -> Unit,
    endDate: LocalDate? = null,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    modifier: Modifier = Modifier,
) {
    var currentYM by remember { mutableStateOf(YearMonth.of(startDate.year, startDate.month)) }
    var isToggleOn by remember { mutableStateOf(endDate != null) }
    var selectedStartDate by remember { mutableStateOf(startDate) }
    var selectedEndDate by remember { mutableStateOf(endDate) }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        containerColor = TogedyTheme.colors.white,
        dragHandle = null,
    ) {
        Column(
            modifier = modifier
                .padding(16.dp),
        ) {
            YearMonthSection(
                year = currentYM.year,
                month = currentYM.monthValue,
                onDateChange = { currentYM = it }
            )

            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = selectedStartDate.toString().replace('-', '.'),
                    style = TogedyTheme.typography.body12m.copy(TogedyTheme.colors.gray600),
                )

                if (selectedEndDate != null) {
                    Text(
                        text = " → ${selectedEndDate.toString().replace('-', '.')}",
                        style = TogedyTheme.typography.body12m.copy(TogedyTheme.colors.gray600),
                    )
                }

                Spacer(Modifier.weight(1f))

                Text(
                    text = "기간 설정",
                    style = TogedyTheme.typography.body12m.copy(TogedyTheme.colors.gray600),
                )

                Spacer(Modifier.width(4.dp))

                TogedyToggleButton(
                    isToggleOn = isToggleOn,
                    onToggleClick = {
                        isToggleOn = !isToggleOn
                        if (!isToggleOn) selectedEndDate = null
                    },
                )
            }

            Spacer(Modifier.height(8.dp))

            CalendarSection(
                currentMonth = YearMonth.of(currentYM.year, currentYM.monthValue),
                selectedStartDate = selectedStartDate,
                selectedEndDate = selectedEndDate,
                onDateSelected = { newDate ->
                    if (isToggleOn) {
                        selectedStartDate =
                            if (newDate <= selectedStartDate || selectedEndDate != null) newDate
                            else selectedStartDate
                        selectedEndDate =
                            if (newDate <= selectedStartDate || selectedEndDate != null) null
                            else newDate
                    } else {
                        selectedStartDate = newDate
                    }
                },
            )

            BottomButtonSection(
                onResetToTodayClick = {
                    selectedStartDate = LocalDate.now()
                    selectedEndDate = null
                },
                onDoneClick = { onDoneClick(selectedStartDate, selectedEndDate) }
            )
        }

        Spacer(Modifier.height(10.dp))
    }
}

@Composable
private fun YearMonthSection(
    year: Int,
    month: Int,
    onDateChange: (YearMonth) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.padding(top = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(ic_left_chevron_green),
            contentDescription = null,
            tint = TogedyTheme.colors.green,
            modifier = Modifier.noRippleClickable {
                if (month == 1) onDateChange(YearMonth.of(year - 1, 12))
                else onDateChange(YearMonth.of(year, month - 1))
            },
        )

        Spacer(Modifier.width(4.dp))

        Text(
            text = "$year. ${month.toString().padStart(2, '0')}",
            style = TogedyTheme.typography.title18sb.copy(TogedyTheme.colors.gray700),
        )

        Spacer(Modifier.width(4.dp))

        Icon(
            imageVector = ImageVector.vectorResource(ic_right_chevron_green),
            contentDescription = null,
            tint = TogedyTheme.colors.green,
            modifier = Modifier.noRippleClickable {
                if (month == 12) onDateChange(YearMonth.of(year + 1, 1))
                else onDateChange(YearMonth.of(year, month + 1))
            },
        )
    }
}

@Composable
private fun CalendarSection(
    currentMonth: YearMonth = YearMonth.now(),
    selectedStartDate: LocalDate,
    selectedEndDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
) {
    val firstOfMonth = currentMonth.atDay(1)
    val firstDayOfWeek = firstOfMonth.dayOfWeek.value % 7

    val daysInMonth = currentMonth.lengthOfMonth()
    val totalDays = firstDayOfWeek + daysInMonth
    val totalCells = ceil(totalDays / 7.0).toInt() * 7
    val startDate = firstOfMonth.minusDays(firstDayOfWeek.toLong())

    val dateList = remember(currentMonth) {
        List(totalCells) { index -> startDate.plusDays(index.toLong()) }
    }

    Column {
        DayOfWeek(modifier = Modifier.padding(vertical = 8.dp))

        HorizontalDivider()

        dateList.chunked(7).forEach { week ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
            ) {
                week.forEach { date ->
                    val isSelected = date == selectedStartDate || date == selectedEndDate
                    val isInCurrentMonth = date.month == currentMonth.month

                    val textColor = when {
                        isSelected -> TogedyTheme.colors.white
                        !isInCurrentMonth -> TogedyTheme.colors.gray400
                        else -> TogedyTheme.colors.gray700
                    }
                    val backgroundColor =
                        if (isSelected) TogedyTheme.colors.green
                        else Color.Transparent

                    val hasRange = selectedEndDate != null

                    val leftBg =
                        if (hasRange && selectedStartDate < date && selectedEndDate >= date)
                            TogedyTheme.colors.greenBg else Color.Transparent

                    val rightBg =
                        if (hasRange && selectedStartDate <= date && selectedEndDate > date)
                            TogedyTheme.colors.greenBg else Color.Transparent

                    Box(
                        modifier = Modifier
                            .height(32.dp)
                            .weight(1f),
                        contentAlignment = Alignment.Center,
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .background(leftBg),
                            )

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .background(rightBg),
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(backgroundColor)
                                .clickable { onDateSelected(date) },
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = date.dayOfMonth.toString(),
                                style = TogedyTheme.typography.body14m.copy(textColor),
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BottomButtonSection(
    onResetToTodayClick: () -> Unit,
    onDoneClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .background(TogedyTheme.colors.gray200, RoundedCornerShape(8.dp))
                .padding(10.dp)
                .noRippleClickable(onResetToTodayClick),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(ic_replay),
                contentDescription = null,
                tint = TogedyTheme.colors.gray800,
            )

            Spacer(Modifier.width(4.dp))

            Text(
                text = "오늘",
                style = TogedyTheme.typography.title16sb.copy(TogedyTheme.colors.gray700),
            )
        }

        Spacer(Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .background(TogedyTheme.colors.green, RoundedCornerShape(8.dp))
                .padding(10.dp)
                .weight(1f)
                .noRippleClickable(onDoneClick),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "일정선택 완료",
                style = TogedyTheme.typography.title16sb.copy(TogedyTheme.colors.white),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
internal fun CalendarBottomSheetPreview(modifier: Modifier = Modifier) {
    TogedyTheme {
        CalendarBottomSheet(
            startDate = LocalDate.now(),
            endDate = null,
            onDismissRequest = {},
            onDoneClick = { start, end -> },
            modifier = modifier,
        )
    }
}
