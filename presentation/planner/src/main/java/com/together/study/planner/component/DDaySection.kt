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
import com.together.study.designsystem.theme.TogedyTheme

@Composable
internal fun DDaySection(
    userScheduleName: String?,
    remainingDays: Int?,
    modifier: Modifier = Modifier,
    textColor: Color = TogedyTheme.colors.gray700,
) {
    val dDayText = when (remainingDays) {
        null -> ""
        0 -> "D-DAY"
        else -> if (remainingDays < 0) "D$remainingDays" else "D+$remainingDays"
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = userScheduleName ?: "",
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
