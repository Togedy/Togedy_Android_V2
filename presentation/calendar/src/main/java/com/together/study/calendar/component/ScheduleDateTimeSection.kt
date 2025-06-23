package com.together.study.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.theme.TogedyTheme
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
internal fun ScheduleDateTimeSection(
    startDateTime: Pair<LocalDate, String?>,
    endDateTime: Pair<LocalDate?, String?>,
    onCalendarOpen: () -> Unit,
    onClockOpen: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val (startDate, startTime) = startDateTime
    val (endDate, endTime) = endDateTime

    val firstBoxText = remember(startDate, startTime, endDate) {
        formatFirstBoxText(startDate, startTime, endDate)
    }

    val secondBoxText = remember(endDate, endTime) {
        formatSecondBoxText(endDate, endTime)
    }

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
        GrayBoxText(
            text = firstBoxText,
            onTextClick = onCalendarOpen,
        )

        secondBoxText?.let {
            Spacer(Modifier.width(8.dp))

            if (it == "+ 시간") {
                GrayBoxText(
                    text = it,
                    onTextClick = onClockOpen,
                )
            } else {
                Text(
                    text = "→",
                    style = TogedyTheme.typography.body14m.copy(TogedyTheme.colors.gray700),
                )
                Spacer(Modifier.width(8.dp))
                GrayBoxText(
                    text = it,
                    onTextClick = onClockOpen,
                )
            }
        }
    }
}

private fun formatFirstBoxText(
    startDate: LocalDate,
    startTime: String?,
    endDate: LocalDate?,
): String = when {
    startTime != null -> "${startDate.monthValue}.${startDate.dayOfMonth}, $startTime"
    endDate != null -> "${startDate.monthValue}.${startDate.dayOfMonth} → ${endDate.monthValue}.${endDate.dayOfMonth}"
    else -> "${startDate.monthValue}.${startDate.dayOfMonth} ${
        startDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN)
    }"
}

private fun formatSecondBoxText(
    endDate: LocalDate?,
    endTime: String?,
): String? = when {
    endDate == null && endTime == null -> "+ 시간"
    endDate != null && endTime == null -> "${endDate.monthValue}.${endDate.dayOfMonth} --"
    endDate != null && endTime != null -> "${endDate.monthValue}.${endDate.dayOfMonth}, $endTime"
    else -> null
}
