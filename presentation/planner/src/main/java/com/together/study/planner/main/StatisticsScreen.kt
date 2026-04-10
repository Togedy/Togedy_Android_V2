package com.together.study.planner.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.common.state.UiState
import com.together.study.designsystem.R.drawable.ic_check_green
import com.together.study.designsystem.component.studyblock.StudyBlock
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.planner.component.StreakSection
import com.together.study.planner.model.DailyStatistics
import java.time.LocalDate

private val daysOfWeek = listOf("월", "화", "수", "목", "금", "토", "일")

@Composable
internal fun StatisticsScreen(
    statisticsState: UiState<DailyStatistics>,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()
    val currentDate = LocalDate.now()

    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.gray100)
            .padding(horizontal = 14.dp),
    ) {
        when (statisticsState) {
            is UiState.Loading -> {}

            is UiState.Failure -> {}

            is UiState.Success -> {
                val statistics = statisticsState.data
                item {
                    Spacer(Modifier.height(14.dp))

                    StreakSection(
                        currentMonth = currentDate.monthValue,
                        daysSinceLastStudy = statistics.daysSinceLastStudy,
                        currentStreakDays = statistics.currentStreakDays,
                    )

                    Spacer(Modifier.height(16.dp))

                    WeeklyStatistics(statistics.weeklyReview)

                    Spacer(Modifier.height(16.dp))

                    MonthlyStatistics(
                        currentDate = currentDate,
                        monthlyReview = statistics.monthlyReview,
                    )

                    Spacer(Modifier.height(16.dp))
                }
            }

            else -> {}
        }
    }
}

@Composable
fun MonthlyStatistics(
    currentDate: LocalDate,
    monthlyReview: List<Int>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(TogedyTheme.colors.white, RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Text(
            text = "\uD83D\uDCC5 월간 리뷰",
            style = TogedyTheme.typography.body13b,
        )

        Spacer(Modifier.height(12.dp))

        StudyBlock(
            currentDate = currentDate,
            studyTimeList = monthlyReview,
            blockSize = 20.dp,
        )
    }
}

@Composable
fun WeeklyStatistics(
    weeklyReview: List<String?>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(TogedyTheme.colors.white, RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Text(
            text = "\uD83D\uDCC5 주간 리뷰",
            style = TogedyTheme.typography.body13b,
        )

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            weeklyReview.forEachIndexed { index, dayRecord ->
                val backgroundColor = when (dayRecord) {
                    "00:00:00" -> TogedyTheme.colors.gray100
                    null -> TogedyTheme.colors.white
                    else -> TogedyTheme.colors.green
                }
                val borderColor =
                    if (dayRecord == null) TogedyTheme.colors.gray200
                    else backgroundColor
                val contentColor = when (dayRecord) {
                    "00:00:00" -> TogedyTheme.colors.gray400
                    null -> TogedyTheme.colors.gray600
                    else -> TogedyTheme.colors.white
                }
                val time = when (dayRecord) {
                    "00:00:00" -> "-"
                    null -> ""
                    else -> dayRecord
                }

                Column(
                    modifier = Modifier.width(44.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(backgroundColor, RoundedCornerShape(10.dp))
                            .border(1.dp, borderColor, RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center,
                    ) {
                        if (dayRecord != null && dayRecord != "00:00:00") {
                            Icon(
                                imageVector = ImageVector.vectorResource(ic_check_green),
                                contentDescription = null,
                                tint = TogedyTheme.colors.white,
                            )
                        } else {
                            Text(
                                text = daysOfWeek[index],
                                style = TogedyTheme.typography.body16m,
                                color = contentColor,
                            )
                        }
                    }

                    Spacer(Modifier.height(4.dp))

                    Text(
                        text = time,
                        style = TogedyTheme.typography.chip10sb,
                        color = TogedyTheme.colors.gray600,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun StatisticsScreenPreview() {
    TogedyTheme {
        StatisticsScreen(
            statisticsState = UiState.Success(
                DailyStatistics(
                    daysSinceLastStudy = 0,
                    currentStreakDays = 5,
                    weeklyReview = listOf(
                        "01:30:00",
                        "00:00:00",
                        null,
                        "02:15:00",
                        "00:45:00",
                        null,
                        "03:00:00",
                    ),
                    monthlyReview = listOf(),
                ),
            ),
        )
    }
}
