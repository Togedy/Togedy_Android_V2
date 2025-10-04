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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.theme.TogedyColors
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.studydetail.detailmain.state.StudyAttendance
import com.together.study.util.toLocalTimeWithSecond
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

private val dayOfWeek = listOf("월", "화", "수", "목", "금", "토", "일")

/**
 * 랭킹에 따른 색상 팔레트 정의
 * @param backgroundItem 배경색 (AttendanceItem 전체)
 * @param borderItem 테두리색 (AttendanceItem 전체)
 * @param dailyBgActive 일별 항목 - 출석 완료 배경색
 * @param dailyBgInactive 일별 항목 - 미출석 배경색
 * @param dailyTextActive 일별 항목 - 출석 완료 텍스트색
 * @param dailyTextInactive 일별 항목 - 미출석 텍스트색
 */
@Immutable
private data class RankingPalette(
    val backgroundItem: Color,
    val borderItem: Color,
    val dailyBgActive: Color,
    val dailyBgInactive: Color,
    val dailyTextActive: Color,
    val dailyTextInactive: Color,
    val dailyRankText: Color,
)

/**
 * 랭킹에 맞는 색상 팔레트를 반환합니다.
 */
@Composable
private fun getPaletteForRanking(ranking: Int, colors: TogedyColors): RankingPalette {
    return when (ranking) {
        1 -> RankingPalette(
            backgroundItem = colors.gold100,
            borderItem = colors.gold100,
            dailyBgActive = colors.gold500,
            dailyBgInactive = colors.gold200,
            dailyTextActive = colors.gold900,
            dailyTextInactive = colors.gold500,
            dailyRankText = colors.gold900,
        )

        2 -> RankingPalette(
            backgroundItem = colors.sliver100,
            borderItem = colors.sliver100,
            dailyBgActive = colors.sliver500,
            dailyBgInactive = colors.sliver200,
            dailyTextActive = colors.sliver900,
            dailyTextInactive = colors.sliver500,
            dailyRankText = colors.sliver900,
        )

        3 -> RankingPalette(
            backgroundItem = colors.bronze100,
            borderItem = colors.bronze100,
            dailyBgActive = colors.bronze500,
            dailyBgInactive = colors.bronze200,
            dailyTextActive = colors.bronze900,
            dailyTextInactive = colors.bronze500,
            dailyRankText = colors.bronze900,
        )

        else -> RankingPalette(
            backgroundItem = colors.white,
            borderItem = colors.gray300,
            dailyBgActive = colors.gray300,
            dailyBgInactive = colors.gray100,
            dailyTextActive = colors.gray500,
            dailyTextInactive = colors.gray500,
            dailyRankText = colors.gray500,
        )
    }
}

private fun isCurrentWeek(targetDate: LocalDate): Boolean {
    val startOfWeek = targetDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
    val endOfWeek = targetDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
    val today = LocalDate.now()

    return today in startOfWeek..endOfWeek
}

private fun formatStudyTime(time: String?): String {
    if (time == null) return ""

    val localTime = time.toLocalTimeWithSecond()
    val hour = if (localTime.hour == 0) "" else "${localTime.hour}H"
    val minute = if (localTime.minute == 0) "" else "${localTime.minute}M"

    return when {
        hour.isEmpty() && minute.isEmpty() -> ""
        hour.isEmpty() -> minute
        minute.isEmpty() -> hour
        else -> "$hour\n$minute"
    }
}

@Composable
internal fun AttendanceItem(
    ranking: Int,
    attendance: StudyAttendance,
    selectedDate: LocalDate,
    modifier: Modifier = Modifier,
) {
    val colors = TogedyTheme.colors
    val palette = getPaletteForRanking(ranking, colors)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, palette.borderItem, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .background(palette.backgroundItem)
            .padding(16.dp),
    ) {
        Row(
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
                    color = palette.dailyRankText,
                )
            }

            Spacer(Modifier.width(8.dp))

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
            val todayDayOfWeek = LocalDate.now().dayOfWeek.value - 1
            val isCurrentWeek = isCurrentWeek(selectedDate)

            attendance.studyTimeList.forEachIndexed { index, time ->
                DailyAttendanceItem(
                    dayIndex = index,
                    studyTime = time,
                    isCurrentWeek = isCurrentWeek,
                    todayDayOfWeek = todayDayOfWeek,
                    palette = palette,
                )
            }
        }
    }
}

@Composable
private fun DailyAttendanceItem(
    dayIndex: Int,
    studyTime: String?,
    isCurrentWeek: Boolean,
    todayDayOfWeek: Int,
    palette: RankingPalette,
) {
    val isAttended = studyTime != null
    val isFutureDay = isCurrentWeek && dayIndex > todayDayOfWeek
    val isToday = isCurrentWeek && dayIndex == todayDayOfWeek
    val colors = TogedyTheme.colors

    val text = if (isAttended) formatStudyTime(studyTime) else dayOfWeek[dayIndex]

    val textStyle: TextStyle
    val textColor: Color

    if (isAttended) {
        textStyle = TogedyTheme.typography.body10m
        textColor = palette.dailyTextActive
    } else {
        textStyle = TogedyTheme.typography.body14m
        textColor = if (isFutureDay) colors.gray500 else palette.dailyTextInactive
    }

    val backgroundColor = when {
        isFutureDay || (isToday && studyTime == null) -> colors.white
        isAttended -> palette.dailyBgActive
        else -> palette.dailyBgInactive
    }

    val borderColor = when {
        isToday -> palette.dailyRankText
        isFutureDay -> colors.gray200
        else -> backgroundColor
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