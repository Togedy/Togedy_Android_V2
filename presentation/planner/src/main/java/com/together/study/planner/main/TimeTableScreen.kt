package com.together.study.planner.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.common.state.UiState
import com.together.study.common.type.planner.PlannerSubjectColor
import com.together.study.designsystem.R.drawable.ic_small_arrow_right
import com.together.study.designsystem.theme.GRAY200
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.designsystem.theme.WHITE
import com.together.study.planner.model.TimeTable
import com.together.study.util.toLocalTimeWithSecond
import java.time.LocalTime

@Composable
internal fun TimeTableScreen(
    timeTableState: UiState<List<TimeTable>>,
) {
    when (timeTableState) {
        is UiState.Loading -> {
            TimeTableContent(timeTables = emptyList())
        }

        is UiState.Success -> {
            TimeTableContent(timeTables = timeTableState.data)
        }

        else -> {}
    }
}

@Composable
fun ShareTimeTableContent(
    timeTables: List<TimeTable>,
    modifier: Modifier = Modifier,
) {
    val hours = (6..23).toList() + (0..5).toList()
    val filledSlots = remember(timeTables) {
        if (timeTables.isEmpty()) {
            List(hours.size) { emptyMap<Int, Color>() }
        } else {
            buildMinuteSlotsFromEvents(hours, timeTables)
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp)
        ) {
            itemsIndexed(hours) { hourIndex, hour ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier.width(28.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = hour.toString().padStart(2, '0'),
                            textAlign = TextAlign.Center,
                            style = TogedyTheme.typography.body10m.copy(
                                color = TogedyTheme.colors.gray500
                            ),
                            modifier = Modifier.align(Alignment.Center),
                        )
                    }

                    Spacer(modifier = Modifier.width(2.dp))

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .border(width = 1.dp, color = GRAY200),
                    ) {
                        Row(
                            modifier = Modifier
                                .height(20.dp)
                                .fillMaxWidth()
                                .background(WHITE),
                        ) {
                            repeat(6) { segment ->
                                Row(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxSize(),
                                ) {
                                    val segmentStartMinute = segment * 10
                                    repeat(10) { minuteOffset ->
                                        val minute = segmentStartMinute + minuteOffset
                                        val filledColor = filledSlots[hourIndex][minute]
                                        val background = filledColor ?: Color.Transparent
                                        Box(
                                            modifier = Modifier
                                                .weight(1f)
                                                .fillMaxSize()
                                                .background(background)
                                        )
                                    }
                                }
                                if (segment < 5) {
                                    Box(
                                        modifier = Modifier
                                            .width(1.dp)
                                            .fillMaxSize()
                                            .background(GRAY200),
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TimeTableContent(
    timeTables: List<TimeTable>,
) {
    val currentTime = LocalTime.now()
    val currentHour = currentTime.hour
    val hours = (6..23).toList() + (0..5).toList()
    val filledSlots = remember(timeTables) {
        if (timeTables.isEmpty()) {
            List(hours.size) { emptyMap<Int, Color>() }
        } else {
            buildMinuteSlotsFromEvents(hours, timeTables)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = GRAY200),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            itemsIndexed(hours) { hourIndex, hour ->
                val isCurrentHour = hour == currentHour
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier.width(32.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = hour.toString().padStart(2, '0'),
                            textAlign = TextAlign.Center,
                            style = if (isCurrentHour) {
                                TogedyTheme.typography.body13b.copy(
                                    color = TogedyTheme.colors.green
                                )
                            } else {
                                TogedyTheme.typography.body10m.copy(
                                    color = TogedyTheme.colors.gray500
                                )
                            },
                            modifier = Modifier.align(Alignment.Center),
                        )
                        if (isCurrentHour) {
                            Icon(
                                imageVector = ImageVector.vectorResource(ic_small_arrow_right),
                                contentDescription = null,
                                tint = TogedyTheme.colors.green,
                                modifier = Modifier.align(Alignment.CenterEnd),
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(2.dp))

                    Column(
                        modifier = Modifier.weight(1f),
                    ) {
                        Row(
                            modifier = Modifier
                                .height(24.dp)
                                .fillMaxWidth()
                                .background(WHITE),
                        ) {
                            repeat(6) { segment ->
                                Row(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxSize(),
                                ) {
                                    val segmentStartMinute = segment * 10
                                    repeat(10) { minuteOffset ->
                                        val minute = segmentStartMinute + minuteOffset
                                        val filledColor = filledSlots[hourIndex][minute]
                                        val background = filledColor ?: Color.Transparent
                                        Box(
                                            modifier = Modifier
                                                .weight(1f)
                                                .fillMaxSize()
                                                .background(background)
                                        )
                                    }
                                }
                                if (segment < 5) {
                                    Box(
                                        modifier = Modifier
                                            .width(1.dp)
                                            .fillMaxSize()
                                            .background(GRAY200),
                                    )
                                }
                            }
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(GRAY200),
                        )
                    }
                }
            }
        }
    }
}

private fun buildMinuteSlotsFromEvents(
    hours: List<Int>,
    events: List<TimeTable>,
): List<Map<Int, Color>> {
    val slots = List(hours.size) { mutableMapOf<Int, Color>() }
    val hourIndexByValue = hours.withIndex().associate { it.value to it.index }
    events.forEach { event ->
        val start = runCatching { event.startTime.toLocalTimeWithSecond() }.getOrNull() ?: return@forEach
        val end = runCatching { event.endTime.toLocalTimeWithSecond() }.getOrNull() ?: return@forEach
        val color = Color(PlannerSubjectColor.fromString(event.subjectColor).color)
        if (start == end) return@forEach
        val crossesMidnight = end.isBefore(start)
        var cursor = start
        while (true) {
            val hourIndex = hourIndexByValue[cursor.hour]
            if (hourIndex != null) {
                slots[hourIndex][cursor.minute] = color
            }
            cursor = cursor.plusMinutes(1)
            if (crossesMidnight) {
                if (cursor == end || (cursor.isAfter(end) && cursor.hour == end.hour)) break
            } else {
                if (!cursor.isBefore(end)) break
            }
        }
    }
    return slots.map { it.toMap() }
}

@Preview(showBackground = true)
@Composable
fun TimeTableScreenPreview() {
    TogedyTheme {
        TimeTableScreen(
            timeTableState = UiState.Success(emptyList()),
        )
    }
}
