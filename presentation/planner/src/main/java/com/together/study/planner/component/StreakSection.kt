package com.together.study.planner.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.planner.type.AbsentRange
import com.together.study.planner.type.StreakRange
import com.together.study.planner.type.StudyStatus
import com.together.study.planner.util.toUiModel

@Composable
internal fun StreakSection(
    currentMonth: Int,
    daysSinceLastStudy: Int,
    currentStreakDays: Int,
    modifier: Modifier = Modifier,
) {
    val days = if (daysSinceLastStudy == 0) currentStreakDays else daysSinceLastStudy
    val status =
        if (daysSinceLastStudy == 0) StudyStatus.Streak(StreakRange.from(currentStreakDays))
        else StudyStatus.Absent(AbsentRange.from(daysSinceLastStudy))
    val uiModel = status.toUiModel()
    val phrase = if (daysSinceLastStudy == 0) "공부했어요" else "기다렸어요"

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(TogedyTheme.colors.white, RoundedCornerShape(8.dp)),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "${currentMonth}월의 진행상황",
                style = TogedyTheme.typography.body12m,
                color = TogedyTheme.colors.gray500,
            )

            Spacer(Modifier.height(2.dp))

            Text(
                text = "${days}일",
                style = TogedyTheme.typography.title24b,
                color = TogedyTheme.colors.gray600,
            )

            Text(
                text = phrase,
                style = TogedyTheme.typography.title18b,
                color = TogedyTheme.colors.gray600,
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = uiModel.text,
                style = TogedyTheme.typography.body12m,
                color = TogedyTheme.colors.gray500,
            )
        }

        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(uiModel.imageRes),
                contentDescription = null,
                modifier = Modifier.clip(RoundedCornerShape(8.dp)),
            )
        }
    }
}