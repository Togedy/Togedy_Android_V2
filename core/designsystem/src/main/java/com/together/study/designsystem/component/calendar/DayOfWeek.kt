package com.together.study.designsystem.component.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.theme.TogedyTheme

@Composable
fun DayOfWeek(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TogedyTheme.typography.body12m,
) {
    val dayOfWeek = listOf("일", "월", "화", "수", "목", "금", "토")

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        dayOfWeek.forEachIndexed { index, day ->
            val textColor =
                if (day == "일") TogedyTheme.colors.red
                else if (day == "토") TogedyTheme.colors.blue
                else TogedyTheme.colors.gray600

            Text(
                text = day,
                style = textStyle,
                color = textColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f),
            )

            if (index < dayOfWeek.lastIndex) {
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DayOfWeekPreview(modifier: Modifier = Modifier) {
    TogedyTheme {
        DayOfWeek(modifier)
    }
}
