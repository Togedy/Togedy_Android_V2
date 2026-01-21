package com.together.study.planner.share

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.together.study.designsystem.R.drawable.ic_left_chevron
import com.together.study.designsystem.component.topbar.TogedyTopBar
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.formatToScheduleDate
import java.time.LocalDate

@Composable
fun PlannerShareRoute(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    PlannerShareScreen(
        context = context,
        timerImageUrl = "",
        timer = "00:00:00",
        currentDate = LocalDate.now(),
        modifier = modifier,
        onBackButtonClick = {},
        onConfirmButtonClick = {},
    )
}

@Composable
fun PlannerShareScreen(
    context: Context,
    timerImageUrl: String,
    timer: String,
    currentDate: LocalDate,
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit,
    onConfirmButtonClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.white),
    ) {
        TogedyTopBar(
            title = "이미지로 공유",
            leftIcon = ImageVector.vectorResource(id = ic_left_chevron),
            rightText = "확인",
            rightTextStyle = TogedyTheme.typography.title16sb.copy(
                color = TogedyTheme.colors.green
            ),
            onLeftClicked = onBackButtonClick,
            onRightClicked = onConfirmButtonClick,
        )

        Spacer(Modifier.height(18.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
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
                    blendMode = BlendMode.Darken
                ),
                modifier = Modifier.height(114.dp),
                error = ColorPainter(Color.White),
                placeholder = ColorPainter(Color.White),
                fallback = ColorPainter(Color.White),
            )

            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Text(
                        text = "Study Time",
                        style = TogedyTheme.typography.body12m,
                        color = TogedyTheme.colors.white,
                    )

                    Text(
                        text = currentDate.formatToScheduleDate(),
                        style = TogedyTheme.typography.body12m,
                        color = TogedyTheme.colors.white,
                    )
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    text = timer,
                    style = TogedyTheme.typography.time40l,
                    color = TogedyTheme.colors.white,
                )
            }

        }
    }
}

@Preview
@Composable
private fun PlannerShareScreenPreview() {
    TogedyTheme {
        PlannerShareScreen(
            context = LocalContext.current,
            timerImageUrl = "",
            timer = "00:00:00",
            currentDate = LocalDate.now(),
            onBackButtonClick = {},
            onConfirmButtonClick = {},
        )
    }
}