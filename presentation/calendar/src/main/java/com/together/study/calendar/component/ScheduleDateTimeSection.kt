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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.component.button.TogedyToggleButton
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.formatToDefaultLocalTime
import com.together.study.util.toLocalTime
import com.together.study.util.toScheduleFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth

@Composable
internal fun ScheduleDateTimeSection(
    startDateTime: Pair<LocalDate, String?>,
    endDateTime: Pair<LocalDate?, String?>,
    onCalendarOpen: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isTimeExist = startDateTime.second == null
    var timeToggleButton by remember { mutableStateOf(isTimeExist) }
    
    val currentTime = LocalTime.now()
    var startDate by remember { mutableStateOf(startDateTime.first) }
    val sTime =
        if (startDateTime.second != null) startDateTime.second?.toLocalTime() else currentTime
    var startTime by remember { mutableStateOf(sTime) }
    val eDate = if (endDateTime.first == null) startDate else endDateTime.first
    var endDate by remember { mutableStateOf(eDate) }
    val eTime =
        if (endDateTime.second != null) endDateTime.second?.toLocalTime() else currentTime.plusHours(
            1
        )
    var endTime by remember { mutableStateOf(eTime) }

    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(TogedyTheme.colors.gray500),
            )

            Spacer(Modifier.width(8.dp))

            Text(
                text = "하루종일",
                style = TogedyTheme.typography.body14m.copy(TogedyTheme.colors.gray700),
            )

            Spacer(Modifier.weight(1f))

            TogedyToggleButton(
                isToggleOn = timeToggleButton,
                onToggleClick = {
                    timeToggleButton = !timeToggleButton
                    if (timeToggleButton) {
                        startTime = null
                        endTime = null
                    } else {
                        val time = LocalTime.now().formatToDefaultLocalTime()
                        startTime = time
                        endTime = time
                    }
                },
            )
        }

        Spacer(Modifier.height(20.dp))

        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(TogedyTheme.colors.gray500),
            )

            Spacer(Modifier.width(8.dp))

            Column(
                verticalArrangement = Arrangement.Top
            ) {
                DateTimeSection(
                    isStartSection = true,
                    date = startDate,
                    time = startTime,
                    onCalendarOpen = onCalendarOpen,
                )

                Spacer(Modifier.height(8.dp))

                DateTimeSection(
                    isStartSection = false,
                    date = endDate!!,
                    time = endTime,
                    onCalendarOpen = onCalendarOpen,
                )
            }
        }
    }
}

@Composable
private fun DateTimeSection(
    isStartSection: Boolean,
    date: LocalDate,
    time: LocalTime?,
    onCalendarOpen: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val textValue = if (isStartSection) "시작" else "종료"

    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            text = textValue,
            style = TogedyTheme.typography.body14m.copy(TogedyTheme.colors.gray700),
        )

        Spacer(Modifier.weight(1f))

        GrayBoxText(
            text = formatToDate(date),
            onTextClick = onCalendarOpen,
        )

        if (time != null) {
            Spacer(Modifier.width(8.dp))

            GrayBoxText(
                text = time.toScheduleFormat(),
                onTextClick = {},
            )
        }
    }
}

private fun formatToDate(date: LocalDate): String =
    "${YearMonth.from(date).monthValue}.${date.dayOfMonth} ${date.dayOfWeek}"

@Preview
@Composable
private fun ScheduleDateTimeSectionPreview(modifier: Modifier = Modifier) {
    ScheduleDateTimeSection(
        startDateTime = Pair(LocalDate.now(), null),
        endDateTime = Pair(null, null),
        onCalendarOpen = {},
        modifier = modifier
    )
}
