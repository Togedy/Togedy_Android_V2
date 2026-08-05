package com.together.study.planner.component

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.common.state.UiState
import com.together.study.designsystem.R.drawable.ic_left_chevron
import com.together.study.designsystem.R.drawable.ic_right_chevron_green
import com.together.study.designsystem.component.calendar.DayOfWeek
import com.together.study.designsystem.component.sheet.TogedyTopSheet
import com.together.study.designsystem.component.textchip.TogedyBasicTextChip
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.getDaysInMonthGrid
import com.together.study.util.noRippleClickable
import java.time.LocalDate

@Composable
fun PlannerCalendarTopSheet(
    isCalendarOpen: Boolean,
    selectedDate: LocalDate,
    monthlyHeatmapState: UiState<List<Int>>,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onDateChange: (LocalDate) -> Unit,
) {
    var days by remember(selectedDate) { mutableStateOf(selectedDate.getDaysInMonthGrid()) }

    TogedyTopSheet(
        visible = isCalendarOpen,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        title = {
            Row(
                modifier = modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(ic_left_chevron),
                    contentDescription = "이전 버튼",
                    tint = TogedyTheme.colors.gray500,
                    modifier = Modifier
                        .noRippleClickable {
                            onDateChange(selectedDate.minusMonths(1))
                        }
                        .padding(horizontal = 12.dp)
                        .size(16.dp),
                )

                Text(
                    text = "${selectedDate.year}년 ${selectedDate.monthValue}월",
                    style = TogedyTheme.typography.title16sb,
                    color = TogedyTheme.colors.gray800,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(4.dp)
                        .noRippleClickable(onDismissRequest),
                )

                Icon(
                    imageVector = ImageVector.vectorResource(ic_right_chevron_green),
                    contentDescription = "다음 버튼",
                    tint = TogedyTheme.colors.gray500,
                    modifier = Modifier
                        .noRippleClickable {
                            onDateChange(selectedDate.plusMonths(1))
                        }
                        .padding(horizontal = 12.dp)
                        .size(16.dp),
                )
            }
        },
        rightButton = {
            TogedyBasicTextChip(
                text = "오늘",
                textColor = TogedyTheme.colors.green,
                backgroundColor = TogedyTheme.colors.greenBg,
                roundedCornerShape = RoundedCornerShape(8.dp),
                horizontalPadding = 8.dp,
                verticalPadding = 4.dp,
                modifier = Modifier
                    .padding(top = 16.dp, end = 20.dp)
                    .noRippleClickable { onDateChange(LocalDate.now()) },
            )
        },
        content = {
            Column(
                modifier = Modifier.padding(
                    start = 20.dp,
                    top = 12.dp,
                    end = 20.dp,
                    bottom = 16.dp
                ),
            ) {
                DayOfWeek()

                HorizontalDivider(modifier = Modifier.padding(top = 8.dp, bottom = 4.dp))

                when (monthlyHeatmapState) {
                    is UiState.Loading -> {
                        Spacer(modifier = Modifier.height(200.dp))
                    }

                    is UiState.Failure -> {}

                    is UiState.Success -> {
                        val data = monthlyHeatmapState.data
                        val today = LocalDate.now()
                        days.chunked(7).forEach { week ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 4.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                week.forEachIndexed { dayIndex, day ->
                                    val dayOfMonth = day.toIntOrNull()
                                    val stack = dayOfMonth
                                        ?.minus(1)
                                        ?.takeIf { it in data.indices }
                                        ?.let { data[it] }
                                        ?: 0

                                    val isToday = dayOfMonth != null &&
                                        today.year == selectedDate.year &&
                                        today.monthValue == selectedDate.monthValue &&
                                        dayOfMonth == today.dayOfMonth

                                    CalendarDayBlock(
                                        day = day,
                                        stack = stack,
                                        isSelected = dayOfMonth == selectedDate.dayOfMonth,
                                        isToday = isToday,
                                        modifier = Modifier
                                            .weight(1f)
                                            .aspectRatio(1f),
                                        onDateClick = {
                                            if (dayOfMonth != null) {
                                                onDateChange(
                                                    LocalDate.of(
                                                        selectedDate.year,
                                                        selectedDate.monthValue,
                                                        dayOfMonth
                                                    )
                                                )
                                            }
                                        }
                                    )

                                    if (dayIndex < 6) {
                                        Spacer(Modifier.width(4.dp))
                                    }
                                }

                                repeat(7 - week.size) {
                                    Spacer(
                                        modifier = Modifier
                                            .weight(1f)
                                            .aspectRatio(1f),
                                    )
                                    Spacer(Modifier.width(4.dp))
                                }
                            }
                        }
                    }

                    else -> {}
                }
            }
        },
    )
}

@Composable
private fun CalendarDayBlock(
    day: String,
    stack: Int,
    isSelected: Boolean,
    isToday: Boolean,
    modifier: Modifier = Modifier,
    onDateClick: () -> Unit,
) {
    val roundedCornerShape = RoundedCornerShape(6.dp)
    val isDayOfMonth = day.isNotEmpty()
    val stackColor = when (stack) {
        1 -> TogedyTheme.colors.white
        2 -> TogedyTheme.colors.green500
        3 -> TogedyTheme.colors.green600
        4 -> TogedyTheme.colors.green800
        5 -> TogedyTheme.colors.green
        else -> TogedyTheme.colors.white
    }
    val textColor =
        if (isSelected) TogedyTheme.colors.white
        else TogedyTheme.colors.gray800

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .run {
                    if (isDayOfMonth) {
                        if (isSelected) background(TogedyTheme.colors.black, roundedCornerShape)
                        else background(stackColor, roundedCornerShape)
                    } else {
                        background(Color.Transparent)
                    }
                }
                .noRippleClickable(onDateClick),
            contentAlignment = Alignment.Center,
        ) {
            if (isDayOfMonth && isToday) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 8.dp)
                        .size(4.dp)
                        .background(
                            color = if (isSelected) TogedyTheme.colors.white else TogedyTheme.colors.black,
                            shape = CircleShape
                        )
                )
            }
            Text(
                text = day,
                style = TogedyTheme.typography.body14m,
                color = textColor,
            )
        }
    }
}

@Preview
@Composable
private fun PlannerCalendarTopSheetPreview() {
    TogedyTheme {
        PlannerCalendarTopSheet(
            isCalendarOpen = true,
            selectedDate = LocalDate.now(),
            monthlyHeatmapState = UiState.Success(
                listOf(
                    0, 1, 2, 3, 4, 5, 0, 1, 2, 3, 4, 5, 0, 1, 2, 3, 4, 5, 0, 1, 2, 3, 4, 5,
                    0, 1, 2, 3, 4, 4, 4
                )
            ),
            onDismissRequest = {},
            onDateChange = {},
        )
    }
}
