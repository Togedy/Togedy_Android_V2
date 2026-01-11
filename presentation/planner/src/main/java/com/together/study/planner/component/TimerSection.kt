package com.together.study.planner.component

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
import com.together.study.designsystem.R.drawable.ic_add_image
import com.together.study.designsystem.R.drawable.ic_play_button
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

@Composable
internal fun TimerSection(
    context: Context,
    timerImageUrl: String,
    timer: String,
    modifier: Modifier = Modifier,
    onPlayButtonClick: () -> Unit,
    onImageEditButtonClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.TopCenter,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 20.dp)
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

            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(
                        text = "Study Time",
                        style = TogedyTheme.typography.body12m,
                        color = TogedyTheme.colors.white,
                    )

                    Spacer(Modifier.height(16.dp))

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

        Icon(
            imageVector = ImageVector.vectorResource(ic_add_image),
            contentDescription = "이미지 추가 버튼",
            tint = TogedyTheme.colors.gray500,
            modifier = Modifier
                .shadow(4.dp, RoundedCornerShape(50.dp))
                .background(TogedyTheme.colors.white, RoundedCornerShape(16.dp))
                .padding(8.dp)
                .noRippleClickable(onImageEditButtonClick),
        )
    }
}

@Preview
@Composable
private fun TimerSectionPreview() {
    TogedyTheme {
        TimerSection(
            context = LocalContext.current,
            timerImageUrl = "",
            timer = "00:00:00",
            onPlayButtonClick = {},
            onImageEditButtonClick = {},
        )
    }
}
