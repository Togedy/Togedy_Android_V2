package com.together.study.calendar.maincalendar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.calendar.model.DDay
import com.together.study.designsystem.R.drawable.ic_down_chevron_16
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.presentation.calendar.R.drawable.ic_volume_16
import com.together.study.presentation.calendar.R.string.calendar_year_month
import java.time.LocalDate

@Composable
internal fun CalendarRoute(
    modifier: Modifier = Modifier,
) {
    CalendarScreen(
        modifier = modifier,
    )
}

@Composable
private fun CalendarScreen(
    modifier: Modifier = Modifier,
) {
    CalendarSuccessScreen(
        notice = "알림을 알립니다!",
        date = LocalDate.now(),
        dDay = DDay(hasDday = true, userScheduleName = "건국대학교 면접날", remainingDays = 30),
        modifier = modifier,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CalendarSuccessScreen(
    notice: String,
    date: LocalDate,
    dDay: DDay,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(color = TogedyTheme.colors.white)
            .padding(horizontal = 16.dp),
    ) {
        stickyHeader {
            NoticeSection(
                notice = notice,
                modifier = Modifier,
            )

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                YearMonthSection(date = date)

                if (dDay.hasDday) DDaySection(dDay = dDay)
            }
        }
    }
}

@Composable
private fun NoticeSection(
    notice: String,
    modifier: Modifier = Modifier,
) {
    if (notice.isNotEmpty()) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .background(
                    color = TogedyTheme.colors.green,
                    shape = RoundedCornerShape(12.dp),
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(ic_volume_16),
                contentDescription = null,
                tint = TogedyTheme.colors.greenBg,
            )

            Spacer(Modifier.width(4.dp))

            Text(
                text = notice,
                style = TogedyTheme.typography.chip10sb,
                color = TogedyTheme.colors.greenBg,
                modifier = Modifier.padding(vertical = 6.dp),
            )
        }
    }
}

@Composable
private fun YearMonthSection(
    date: LocalDate,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(calendar_year_month, date.year, date.month.value),
            style = TogedyTheme.typography.title16sb,
            color = TogedyTheme.colors.gray700,
        )

        Spacer(Modifier.width(2.dp))

        Icon(
            imageVector = ImageVector.vectorResource(ic_down_chevron_16),
            contentDescription = null,
            tint = TogedyTheme.colors.gray700,
        )
    }
}

@Composable
private fun DDaySection(
    dDay: DDay,
    modifier: Modifier = Modifier,
) {
    val dDayTextStyle = TogedyTheme.typography.body14m.copy(
        color = TogedyTheme.colors.gray500,
    )
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = dDay.userScheduleName.toString(),
            style = dDayTextStyle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f),
        )

        Spacer(Modifier.width(4.dp))

        Text(
            text = "D-${dDay.remainingDays}",
            style = dDayTextStyle,
        )
    }
}


@Preview
@Composable
private fun CalendarSuccessScreenPreview(modifier: Modifier = Modifier) {
    TogedyTheme {
        CalendarSuccessScreen(
            notice = "알림을 알립니다!",
            date = LocalDate.now(),
            dDay = DDay.mock,
            modifier = modifier,
        )
    }
}