package com.together.study.planner.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.together.study.designsystem.R.drawable.ic_left_chevron
import com.together.study.designsystem.R.drawable.ic_right_chevron_green
import com.together.study.designsystem.component.calendar.DayOfWeek
import com.together.study.designsystem.component.sheet.TogedyTopSheet
import com.together.study.designsystem.component.textchip.TogedyBasicTextChip
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.WeekFields

@Composable
fun PlannerCalendarTopSheet(
    isCalendarOpen: Boolean,
    selectedDate: LocalDate,
    studyTimeList: List<Int>,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onDateChange: (LocalDate) -> Unit,
) {
    var days by remember(selectedDate) {
        mutableStateOf(getDaysInMonthGrid(selectedDate.year, selectedDate.monthValue))
    }

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
                        .size(16.dp)
                        .noRippleClickable {
                            onDateChange(selectedDate.minusMonths(1))
                        },
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
                        .size(16.dp)
                        .noRippleClickable {
                            onDateChange(selectedDate.plusMonths(1))
                        },
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

                days.chunked(7).forEachIndexed { weekIndex, week ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        week.forEachIndexed { dayIndex, day ->
                            val colorIndex = weekIndex * 7 + dayIndex
                            val stack =
                                if (colorIndex >= studyTimeList.size) 0
                                else studyTimeList[colorIndex]

                            CalendarDayBlock(
                                day = day,
                                stack = stack,
                                isSelected = day == selectedDate.dayOfMonth.toString(),
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f),
                                onDateClick = {
                                    onDateChange(
                                        LocalDate.of(
                                            selectedDate.year,
                                            selectedDate.monthValue,
                                            day.toInt()
                                        )
                                    )
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
        },
    )
}

@Composable
private fun CalendarDayBlock(
    day: String,
    stack: Int,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onDateClick: () -> Unit,
) {
    val roundedCornerShape = RoundedCornerShape(6.dp)
    val isDayOfMonth = day.isNotEmpty()
    val stackColor = when (stack) {
        1 -> TogedyTheme.colors.gray200
        2 -> TogedyTheme.colors.green500
        3 -> TogedyTheme.colors.green600
        4 -> TogedyTheme.colors.green800
        5 -> TogedyTheme.colors.green
        else -> TogedyTheme.colors.gray200
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
            Text(
                text = day,
                style = TogedyTheme.typography.body14m,
                color = textColor,
            )
        }
    }
}

private fun getDaysInMonthGrid(year: Int, month: Int): List<String> {
    val yearMonth = YearMonth.of(year, month)
    val firstDayOfMonth = yearMonth.atDay(1)
    val weekFields = WeekFields.of(DayOfWeek.SUNDAY, 1)
    val firstDayOfWeekIndex = firstDayOfMonth.get(weekFields.dayOfWeek())
    val daysInMonth = yearMonth.lengthOfMonth()
    val daysList = mutableListOf<String>()

    repeat(firstDayOfWeekIndex - 1) {
        daysList.add("")
    }

    (1..daysInMonth).forEach { day ->
        daysList.add(day.toString())
    }

    return daysList
}

@Preview
@Composable
private fun PlannerCalendarTopSheetPreview() {
    TogedyTheme {
        PlannerCalendarTopSheet(
            isCalendarOpen = true,
            selectedDate = LocalDate.now(),
            studyTimeList = emptyList(),
            onDismissRequest = {},
            onDateChange = {},
        )
    }
}
