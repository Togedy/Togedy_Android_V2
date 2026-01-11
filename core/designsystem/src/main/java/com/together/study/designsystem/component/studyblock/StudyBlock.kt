package com.together.study.designsystem.component.studyblock

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.component.calendar.DayOfWeek
import com.together.study.designsystem.theme.TogedyTheme
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.temporal.WeekFields

@Composable
fun StudyBlock(
    year: Int,
    month: Int,
    studyTimeList: List<Int>,
    blockSize: Dp? = null,
) {
    val days = getDaysInMonthGrid(year, month)

    Column {
        DayOfWeek()

        Spacer(Modifier.height(10.dp))

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
                        blockSize = blockSize,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
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
}

@Composable
private fun CalendarDayBlock(
    day: String,
    stack: Int,
    blockSize: Dp?,
    modifier: Modifier = Modifier,
) {
    val isDayOfMonth = day.isNotEmpty()
    val stackColor = when (stack) {
        1 -> TogedyTheme.colors.gray200
        2 -> TogedyTheme.colors.green500
        3 -> TogedyTheme.colors.green600
        4 -> TogedyTheme.colors.green800
        5 -> TogedyTheme.colors.green
        else -> TogedyTheme.colors.gray200
    }
    val blockModifier =
        if (blockSize != null) Modifier.size(blockSize)
        else modifier

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {
        Box(modifier = blockModifier.run {
            if (isDayOfMonth) {
                background(stackColor, RoundedCornerShape(6.dp))
            } else {
                background(Color.Transparent)
            }
        })
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
private fun StudyBlockPreview() {
    TogedyTheme {
        StudyBlock(
            year = 2025,
            month = 10,
            studyTimeList = emptyList(),
        )
    }
}
