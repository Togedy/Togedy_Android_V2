package com.together.study.calendar.maincalendar.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.calendar.model.Schedule
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.toLocalDate
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month

@Composable
internal fun WeekSchedule(
    weekDates: List<LocalDate>,
    schedules: List<Schedule>,
    currentMonth: Month,
    onDateClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
) {
    val weekStart = weekDates.first()
    val weekEnd = weekDates.last()

    val filteredSchedules = schedules.filter { schedule ->
        val startDate = schedule.startDate.toLocalDate() ?: return@filter false
        val endDate = schedule.endDate?.toLocalDate() ?: startDate
        !(endDate < weekStart || startDate > weekEnd)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 120.dp),
    ) {
        Column(
            modifier = Modifier,
        ) {
            HorizontalDivider(
                thickness = 1.dp,
                color = TogedyTheme.colors.gray200,
            )

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 2.dp),
            ) {
                weekDates.forEachIndexed { index, date ->
                    val isCurrentMonth = date.month == currentMonth
                    val dayOfWeek = date.dayOfWeek

                    val textColor = when {
                        !isCurrentMonth -> TogedyTheme.colors.gray400
                        dayOfWeek == DayOfWeek.SUNDAY -> TogedyTheme.colors.red
                        dayOfWeek == DayOfWeek.SATURDAY -> TogedyTheme.colors.blue
                        else -> TogedyTheme.colors.gray700
                    }

                    Box(modifier = Modifier.weight(1f)) {
                        Text(
                            text = date.dayOfMonth.toString(),
                            style = TogedyTheme.typography.body10m,
                            color = textColor,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }

                    if (index < weekDates.lastIndex) {
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }
            }

            val placed = mutableListOf<MutableList<Schedule?>>()

            filteredSchedules.forEach { schedule ->
                val start = schedule.startDate.toLocalDate()?.coerceAtLeast(weekStart)
                val end = (schedule.endDate.toLocalDate() ?: schedule.startDate.toLocalDate())
                    ?.coerceAtMost(weekEnd)

                val startIdx = weekDates.indexOfFirst { it == start }
                val endIdx = weekDates.indexOfLast { it == end }

                val rowIndex = placed.indexOfFirst { row ->
                    (startIdx..endIdx).all { row[it] == null }
                }.takeIf { it >= 0 } ?: run {
                    placed.add(MutableList(7) { null })
                    placed.lastIndex
                }

                for (i in startIdx..endIdx) {
                    placed[rowIndex][i] = schedule
                }
            }

            placed.forEachIndexed { index, row ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    var i = 0
                    while (i < 7) {
                        val schedule = row[i]
                        if (schedule != null) {
                            var j = i
                            while (j + 1 < 7 && row[j + 1] == schedule) {
                                j++
                            }
                            val span = j - i + 1

                            MonthlyScheduleItem(
                                schedule = schedule,
                                modifier = Modifier.weight(span.toFloat())
                            )

                            i = j + 1
                        } else {
                            Box(modifier = Modifier.weight(1f))
                            i++
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier.matchParentSize()
        ) {
            weekDates.forEachIndexed { index, date ->
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .clickable { onDateClick(date) },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeekSchedulePreview(modifier: Modifier = Modifier) {
    TogedyTheme {
        val baseDate = LocalDate.of(2025, 6, 1)
        val weekDates = (0..6).map { baseDate.plusDays(it.toLong()) }
        Column {
            WeekSchedule(
                weekDates = weekDates,
                schedules = Schedule.mock,
                currentMonth = Month.JUNE,
                onDateClick = { },
                modifier = modifier
            )
        }
    }
}
