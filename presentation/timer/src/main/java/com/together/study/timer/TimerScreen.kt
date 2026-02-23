package com.together.study.timer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.R.drawable.ic_delete_x_16
import com.together.study.designsystem.component.topbar.TogedyTopBar
import com.together.study.designsystem.theme.TogedyTheme

@Composable
internal fun TimerRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TimerScreen(
        modifier = modifier,
        onBackClick = onBackClick,
    )
}

@Composable
private fun TimerScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.black)
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TogedyTopBar(
            title = "Timer",
            titleStyle = TogedyTheme.typography.title16sb.copy(color = TogedyTheme.colors.white),
            leftIcon = ImageVector.vectorResource(ic_delete_x_16),
            leftIconColor = TogedyTheme.colors.white,
            onLeftClicked = onBackClick,
        )

        Spacer(Modifier.weight(1f))

        Box(
            modifier = Modifier
                .size(screenWidthDp * 1.5f)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            TogedyTheme.colors.green,
                            TogedyTheme.colors.black
                        ),
                        radius = 600f
                    ),
                ),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(500.dp))
                    .border(
                        2.dp,
                        TogedyTheme.colors.green.copy(alpha = 0.8f),
                        RoundedCornerShape(500.dp)
                    )
                    .background(
                        color = TogedyTheme.colors.black,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center,
            ) {

            }
        }

        Spacer(Modifier.weight(1f))

    }
}

@Preview
@Composable
private fun TimerScreenPreview() {
    TogedyTheme {
        TimerScreen(
            onBackClick = {},
        )
    }
}