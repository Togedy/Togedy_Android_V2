package com.together.study.studydetail.detailmain.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.studydetail.detailmain.state.StudyAttendance
import com.together.study.util.toLocalTimeWithSecond
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

private val dayOfWeek = listOf("월", "화", "수", "목", "금", "토", "일")

@Composable
internal fun AttendanceItem(
    ranking: Int,
    attendance: StudyAttendance,
    selectedDate: LocalDate,
    modifier: Modifier = Modifier,
) {
    val isTodayWeek = getWeekDatesAndCheckIfCurrent(selectedDate)
    val todayDayOfWeek = LocalDate.now().dayOfWeek.value - 1
    val colors = TogedyTheme.colors
    val backgroundColor = when (ranking) {
        1 -> colors.gold100
        2 -> colors.sliver100
        3 -> colors.bronze100
        else -> colors.white
    }
    val borderColor = when (ranking) {
        1 -> colors.gold100
        2 -> colors.sliver100
        3 -> colors.bronze100
        else -> colors.gray300
    }

    val dailyItemColor = when (ranking) {
        1 -> Triple(colors.gold200, colors.gold500, colors.gold900)
        2 -> Triple(colors.sliver200, colors.sliver500, colors.sliver900)
        3 -> Triple(colors.bronze200, colors.bronze500, colors.bronze900)
        else -> Triple(colors.gray100, colors.gray300, colors.gray500)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, borderColor, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                Modifier
                    .size(24.dp)
                    .background(TogedyTheme.colors.white),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = ranking.toString(),
                    style = TogedyTheme.typography.body14b,
                    color = dailyItemColor.third,
                )
            }

            Text(
                text = attendance.userName,
                style = TogedyTheme.typography.body14b,
                color = colors.gray800,
                maxLines = 1,
            )
        }

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            attendance.studyTimeList.forEachIndexed { index, time ->
                val text = if (time != null) getTimeMinute(time) else dayOfWeek[index]
                val textStyle =
                    if (time != null) TogedyTheme.typography.body10m
                    else TogedyTheme.typography.body14m
                val backgroundColor =
                    if (isTodayWeek && index == todayDayOfWeek && time == null) colors.white
                    else if (isTodayWeek && index > todayDayOfWeek) colors.white
                    else {
                        if (time == null) dailyItemColor.first
                        else dailyItemColor.second
                    }
                val borderColor =
                    if (isTodayWeek && index == todayDayOfWeek) dailyItemColor.third
                    else if (isTodayWeek && index > todayDayOfWeek) colors.gray200
                    else backgroundColor
                val textColor =
                    if (isTodayWeek && index > todayDayOfWeek) colors.gray500
                    else {
                        if (time == null) dailyItemColor.second
                        else dailyItemColor.third
                    }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .size(40.dp)
                        .border(1.5.dp, borderColor, RoundedCornerShape(12.dp))
                        .background(backgroundColor),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = text,
                        style = textStyle,
                        color = textColor,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

private fun getWeekDatesAndCheckIfCurrent(targetDate: LocalDate): Boolean {
    val startOfWeek = targetDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
    val endOfWeek = targetDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
    val today = LocalDate.now()

    return today in startOfWeek..endOfWeek
}

private fun getTimeMinute(time: String): String {
    val localTime = time.toLocalTimeWithSecond()
    val hour = if (localTime.hour == 0) "" else "${localTime.hour}H"
    val minute = if (localTime.minute == 0) "" else "${localTime.minute}M"

    return if (hour.isEmpty()) minute
    else if (minute.isEmpty()) hour
    else hour + "\n" + minute
}

@Preview
@Composable
private fun AttendanceItemPreview() {
    TogedyTheme {
        Column {
            AttendanceItem(
                1,
                StudyAttendance.mock,
                selectedDate = LocalDate.now()
            )
            AttendanceItem(
                2,
                StudyAttendance.mock,
                selectedDate = LocalDate.now()
            )
            AttendanceItem(
                3,
                StudyAttendance.mock,
                selectedDate = LocalDate.now()
            )
            AttendanceItem(
                4,
                StudyAttendance.mock2,
                selectedDate = LocalDate.now()
            )
            AttendanceItem(
                4,
                StudyAttendance.mock,
                selectedDate = LocalDate.now()
            )
        }
    }
}