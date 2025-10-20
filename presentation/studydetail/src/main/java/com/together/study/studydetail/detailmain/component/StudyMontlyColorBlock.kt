package com.together.study.studydetail.detailmain.component

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.component.calendar.DayOfWeek
import com.together.study.designsystem.theme.TogedyTheme
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.temporal.WeekFields

@Composable
internal fun StudyMonthlyColorBlock(
    year: Int,
    month: Int,
    studyTimeList: List<Int>,
    modifier: Modifier = Modifier,
) {
    val days = getDaysInMonthGrid(year, month)

    Column(
        modifier = modifier
            .width(228.dp)
            .background(
                color = TogedyTheme.colors.gray50,
                shape = RoundedCornerShape(8.dp),
            )
            .padding(12.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = year.toString(),
                style = TogedyTheme.typography.body12m,
                color = TogedyTheme.colors.gray500,
            )

            Text(
                text = "${month}월",
                style = TogedyTheme.typography.body10m,
                color = TogedyTheme.colors.gray800,
            )
        }

        DayOfWeek()

        Spacer(Modifier.height(10.dp))

        days.chunked(7).forEachIndexed { weekIndex, week ->
            Row(
                modifier = modifier
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
                        modifier = Modifier.weight(1f)
                    )

                    if (dayIndex < 6) {
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }

                val remainingSlots = 7 - week.size
                if (remainingSlots > 0) {
                    repeat(remainingSlots) {
                        Spacer(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .padding(2.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun CalendarDayBlock(
    day: String,
    stack: Int,
    modifier: Modifier = Modifier,
) {
    val isDayOfMonth = day.isNotEmpty()
    val stackColor = when (stack) {
        1 -> TogedyTheme.colors.gray200
        2 -> TogedyTheme.colors.green500
        3 -> TogedyTheme.colors.green600
        4 -> TogedyTheme.colors.green800
        5 -> TogedyTheme.colors.green
        else -> TogedyTheme.colors.black
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .aspectRatio(1f)
            .run {
                if (isDayOfMonth) {
                    background(stackColor, RoundedCornerShape(6.dp))
                } else {
                    background(Color.Transparent)
                }
            }
    ) { }
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
private fun StudyMonthlyColorBlockPreview() {
    TogedyTheme {
        StudyMonthlyColorBlock(
            year = 2025,
            month = 10,
            studyTimeList = StudyMemberTimeBlocks.MonthlyStudyTime.mock.studyTimeList,
        )
    }
}
