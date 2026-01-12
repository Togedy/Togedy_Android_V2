package com.together.study.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.component.button.TogedyToggleButton
import com.together.study.designsystem.component.wheelpicker.TogedyTimePicker
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.formatToDefaultLocalTime
import com.together.study.util.formatToScheduleDate
import com.together.study.util.toLocalTime
import com.together.study.util.toScheduleFormat
import java.time.LocalDate
import java.time.LocalTime

@Composable
internal fun ScheduleDateTimeSection(
    startDateTime: Pair<LocalDate, String?>,
    endDateTime: Pair<LocalDate?, String?>,
    onCalendarOpen: () -> Unit,
    onTimeChange: (LocalTime?, LocalTime?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val currentTime = LocalTime.of(LocalTime.now().hour, 0)
    val startDate = startDateTime.first
    val endDate = endDateTime.first ?: startDate

    var startTime by remember { mutableStateOf(startDateTime.second?.toLocalTime() ?: currentTime) }
    var endTime by remember {
        mutableStateOf(
            endDateTime.second?.toLocalTime() ?: currentTime.plusHours(1)
        )
    }

    var isAllDay by remember { mutableStateOf(startTime == null) }

    LaunchedEffect(startTime, endTime) {
        onTimeChange(startTime, endTime)
    }

    Column(modifier = modifier) {
        ToggleRow(
            title = "하루종일",
            isChecked = isAllDay,
            onToggleChange = { checked ->
                isAllDay = checked
                if (checked) {
                    startTime = null
                    endTime = null
                } else {
                    val now = LocalTime.now().formatToDefaultLocalTime()
                    startTime = LocalTime.of(now.hour, 0)
                    endTime = LocalTime.of(now.hour + 1, 0)
                }
            }
        )

        Spacer(Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(TogedyTheme.colors.gray500),
            )

            Spacer(Modifier.width(8.dp))

            Column(verticalArrangement = Arrangement.Top) {
                DateTimeSection(
                    label = "시작",
                    date = startDate,
                    time = startTime,
                    onCalendarOpen = onCalendarOpen,
                    onTimeChange = { hour, minute ->
                        startTime =
                            if (hour != null && minute != null) LocalTime.of(hour, minute) else null
                    }
                )

                Spacer(Modifier.height(8.dp))

                DateTimeSection(
                    label = "종료",
                    date = endDate,
                    time = endTime,
                    onCalendarOpen = onCalendarOpen,
                    onTimeChange = { hour, minute ->
                        endTime =
                            if (hour != null && minute != null) LocalTime.of(hour, minute) else null
                    }
                )
            }
        }
    }
}

@Composable
private fun ToggleRow(
    title: String,
    isChecked: Boolean,
    onToggleChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(TogedyTheme.colors.gray500),
        )

        Spacer(Modifier.width(8.dp))

        Text(
            text = title,
            style = TogedyTheme.typography.body14m.copy(TogedyTheme.colors.gray700),
        )

        Spacer(Modifier.weight(1f))

        TogedyToggleButton(
            isToggleOn = isChecked,
            onToggleClick = { onToggleChange(!isChecked) }
        )
    }
}

@Composable
private fun DateTimeSection(
    label: String,
    date: LocalDate,
    time: LocalTime?,
    onCalendarOpen: () -> Unit,
    onTimeChange: (Int?, Int?) -> Unit,
) {
    var isTimePickerOpen by remember { mutableStateOf(false) }
    var selectedHour by remember { mutableStateOf(time?.hour) }
    var selectedMinute by remember { mutableStateOf(time?.minute) }

    val timeTextColor =
        if (isTimePickerOpen) TogedyTheme.colors.green
        else TogedyTheme.colors.gray500

    LaunchedEffect(selectedHour, selectedMinute) {
        onTimeChange(selectedHour, selectedMinute)
    }

    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = label,
                style = TogedyTheme.typography.body14m.copy(TogedyTheme.colors.gray700),
            )

            Spacer(Modifier.weight(1f))

            GrayBoxText(
                text = date.formatToScheduleDate(),
                onTextClick = onCalendarOpen,
            )

            if (time != null) {
                Spacer(Modifier.width(8.dp))

                GrayBoxText(
                    text = time.toScheduleFormat(),
                    textColor = timeTextColor,
                    onTextClick = { isTimePickerOpen = !isTimePickerOpen },
                )
            }
        }

        if (isTimePickerOpen) {
            TogedyTimePicker(
                initHour = selectedHour ?: 0,
                initMinute = selectedMinute ?: 0,
                onValueChange = { hour, minute ->
                    selectedHour = hour
                    selectedMinute = minute
                }
            )
        }
    }
}

@Preview
@Composable
private fun ScheduleDateTimeSectionPreview() {
    ScheduleDateTimeSection(
        startDateTime = Pair(LocalDate.now(), null),
        endDateTime = Pair(null, null),
        onCalendarOpen = {},
        onTimeChange = { start, end -> }
    )
}
