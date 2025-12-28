package com.together.study.planner.main

import android.content.Context
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.together.study.calendar.model.DDay
import com.together.study.designsystem.R.drawable.ic_left_chevron
import com.together.study.designsystem.R.drawable.ic_play_button
import com.together.study.designsystem.R.drawable.ic_right_chevron_green
import com.together.study.designsystem.R.drawable.img_study_background
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable
import java.time.LocalDate

@Composable
fun PlannerScreen(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.white)
            .padding(top = 20.dp),
    ) {
        PlannerTopSection(
            selectedDate = selectedDate,
            dDay = DDay(true, "수능", -100),
            onDayBeforeClick = { selectedDate = selectedDate.minusDays(1) },
            onDayAfterClick = { selectedDate = selectedDate.plusDays(1) },
            onCalendarClick = { },
            onShareClick = { },
        )

        Spacer(Modifier.height(16.dp))

        TimerSection(
            context = context,
            timerImageUrl = "",
            timer = "00:00:00",
            onPlayButtonClick = { },
        )

    }
}

@Composable
private fun PlannerTopSection(
    selectedDate: LocalDate,
    dDay: DDay,
    onDayBeforeClick: () -> Unit,
    onDayAfterClick: () -> Unit,
    onCalendarClick: () -> Unit,
    onShareClick: () -> Unit,
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.TopEnd,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(ic_left_chevron),
                    contentDescription = "이전 버튼",
                    tint = TogedyTheme.colors.gray500,
                    modifier = Modifier
                        .size(16.dp)
                        .noRippleClickable(onDayBeforeClick),
                )

                Text(
                    text = "${selectedDate.year}년 ${selectedDate.monthValue}월 ${selectedDate.dayOfMonth}일",
                    style = TogedyTheme.typography.title16sb,
                    color = TogedyTheme.colors.gray800,
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .noRippleClickable(onCalendarClick),
                )

                Icon(
                    imageVector = ImageVector.vectorResource(ic_right_chevron_green),
                    contentDescription = "다음 버튼",
                    tint = TogedyTheme.colors.gray500,
                    modifier = Modifier
                        .size(16.dp)
                        .noRippleClickable(onDayAfterClick),
                )
            }

            Icon(
                imageVector = ImageVector.vectorResource(ic_right_chevron_green),
                contentDescription = "공유 버튼",
                tint = TogedyTheme.colors.gray500,
                modifier = Modifier
                    .size(20.dp)
                    .noRippleClickable(onShareClick),
            )
        }

        if (dDay.hasDday) {
            val dDayText = when {
                dDay.remainingDays == 0 -> "D-DAY"
                dDay.remainingDays!! < 0 -> "D${dDay.remainingDays}"
                else -> "D+${dDay.remainingDays}"
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "${dDay.userScheduleName}",
                    style = TogedyTheme.typography.body10m,
                    color = TogedyTheme.colors.green,
                    modifier = Modifier.padding(horizontal = 3.dp),
                )

                Text(
                    text = dDayText,
                    style = TogedyTheme.typography.body10m,
                    color = TogedyTheme.colors.gray700,
                    modifier = Modifier.padding(horizontal = 3.dp),
                )
            }
        }
    }
}

@Composable
private fun TimerSection(
    context: Context,
    timerImageUrl: String,
    timer: String,
    modifier: Modifier = Modifier,
    onPlayButtonClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(114.dp)
                .padding(top = 20.dp)
                .clip(RoundedCornerShape(16.dp)),
        ) {
            AsyncImage(
                model = ImageRequest
                    .Builder(context)
                    .data(timerImageUrl)
                    .placeholder(img_study_background)
                    .error(img_study_background)
                    .fallback(img_study_background)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )

            Row(
                modifier = Modifier.padding(16.dp),
            ) {
                Column {
                    Text(
                        text = "Study Time",
                        style = TogedyTheme.typography.body12m,
                        color = TogedyTheme.colors.white,
                    )

                    Text(
                        text = timer,
                        style = TogedyTheme.typography.time40l,
                        color = TogedyTheme.colors.white,
                    )
                }

                Spacer(Modifier.weight(1f))

                Icon(
                    imageVector = ImageVector.vectorResource(ic_play_button),
                    contentDescription = "타이머버튼",
                    tint = Color.Unspecified,
                    modifier = Modifier.noRippleClickable(onPlayButtonClick)
                )
            }
        }


    }
}

@Preview
@Composable
private fun PlannerScreenPreview() {
    TogedyTheme {
        PlannerScreen()
    }
}