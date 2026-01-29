package com.together.study.planner.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.together.study.calendar.model.DDay
import com.together.study.designsystem.theme.TogedyTheme

@Composable
internal fun TodoSection(
    dDay: DDay,
    modifier: Modifier = Modifier,
    textColor: Color = TogedyTheme.colors.gray700,
) {
    val dDayText = when (val days = dDay.remainingDays) {
        null -> ""
        0 -> "D-DAY"
        else -> if (days < 0) "D$days" else "D+$days"
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = dDay.userScheduleName ?: "",
            style = TogedyTheme.typography.body10m,
            color = TogedyTheme.colors.green,
            modifier = Modifier.padding(horizontal = 3.dp),
        )

        Text(
            text = dDayText,
            style = TogedyTheme.typography.body10m,
            color = textColor,
            modifier = Modifier.padding(horizontal = 3.dp),
        )
    }
}