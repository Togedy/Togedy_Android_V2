package com.together.study.planner.share.component

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.together.study.calendar.model.DDay
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.planner.component.TodoSection
import com.together.study.util.formatToYearMonthDate
import java.time.LocalDate

@Composable
internal fun ShareTimerSection(
    context: Context,
    timerImageUrl: String,
    currentDate: LocalDate,
    timer: String,
    dDay: DDay,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(16.dp)),
    ) {
        AsyncImage(
            model = ImageRequest
                .Builder(context)
                .data(timerImageUrl)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(
                color = TogedyTheme.colors.gray500.copy(alpha = 0.5f),
                blendMode = BlendMode.Darken,
            ),
            modifier = Modifier.height(114.dp),
            error = ColorPainter(Color.White),
            placeholder = ColorPainter(Color.White),
            fallback = ColorPainter(Color.White),
        )

        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                Text(
                    text = "Study Time",
                    style = TogedyTheme.typography.body12m,
                    color = TogedyTheme.colors.white,
                )

                Text(
                    text = currentDate.formatToYearMonthDate(),
                    style = TogedyTheme.typography.body12m,
                    color = TogedyTheme.colors.white,
                )
            }

            Spacer(Modifier.height(4.dp))

            Text(
                text = timer.toTimerType(),
                style = TogedyTheme.typography.time40l,
                fontWeight = FontWeight.ExtraBold,
                color = TogedyTheme.colors.white,
            )

            if (dDay.hasDday) {
                TodoSection(
                    dDay = dDay,
                    textColor = TogedyTheme.colors.white,
                )
            } else {
                Spacer(Modifier.height(14.dp))
            }
        }
    }
}

private fun String.toTimerType(): String {
    val (h, m, s) = this.split(":")
    return "${h}h ${m}m ${s}s"
}

@Preview
@Composable
private fun ShareTimerSectionPreview() {
    TogedyTheme {
        ShareTimerSection(
            context = LocalContext.current,
            timerImageUrl = "",
            currentDate = LocalDate.now(),
            timer = "00:00:00",
            dDay = DDay(true, "수능", 50),
        )
    }
}
