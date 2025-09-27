package com.together.study.study.main.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.R.drawable.img_character_achieved
import com.together.study.designsystem.R.drawable.img_character_heart
import com.together.study.designsystem.R.drawable.img_character_speaker
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.study.main.state.TimerInfo

@Composable
internal fun TimerSection(
    timerInfo: TimerInfo,
) {
    val imageResource =
        if (timerInfo.hasChallenge) {
            if (timerInfo.achievement!! >= 100) img_character_achieved
            else img_character_heart
        } else {
            img_character_speaker
        }
    val studyTime = timerInfo.studyTime ?: "00:00:00"
    val timerColor =
        if (timerInfo.studyTime == null) TogedyTheme.colors.gray700
        else TogedyTheme.colors.green

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(TogedyTheme.colors.black)
            .padding(vertical = 32.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(imageResource),
                contentDescription = null,
            )

            if (timerInfo.hasChallenge) {
                Row {
                    listOf(
                        "목표 ${timerInfo.goalTime}" to TogedyTheme.colors.gray500,
                        " | " to TogedyTheme.colors.gray800,
                        "${timerInfo.achievement}%" to TogedyTheme.colors.green
                    ).forEach { (text, color) ->
                        Text(
                            text = text,
                            style = TogedyTheme.typography.body13m,
                            color = color
                        )
                    }
                }
            } else {
                Text(
                    text = "오늘도 열심히!!",
                    style = TogedyTheme.typography.body13m,
                    color = TogedyTheme.colors.gray500
                )
            }

            Text(
                text = studyTime,
                style = TogedyTheme.typography.time40l,
                color = timerColor,
            )

            Box(
                modifier = Modifier.size(30.dp)
            )

            Spacer(Modifier.height(20.dp))
        }

        if (timerInfo.studyTime == null) {
            AnimatedRingProgress(0f)
        } else {
            if (timerInfo.hasChallenge) {
                AnimatedRingProgress(timerInfo.achievement!! * 0.01f)
            } else {
                AnimatedRingProgress(
                    1f,
                    listOf(TogedyTheme.colors.green, TogedyTheme.colors.green),
                )
            }
        }
    }
}

@Composable
fun AnimatedRingProgress(
    targetProgress: Float,
    gradientColors: List<Color> = listOf(
        TogedyTheme.colors.green,
        TogedyTheme.colors.greenBg,
        TogedyTheme.colors.green
    ),
) {
    val animatedProgress by animateFloatAsState(
        targetValue = targetProgress,
        animationSpec = tween(durationMillis = 1000)
    )

    RingProgress(
        progress = animatedProgress,
        gradientColors = gradientColors,
        modifier = Modifier.size(240.dp),
    )
}

@Composable
fun RingProgress(
    progress: Float,
    gradientColors: List<Color>,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 4.dp,
    backgroundColor: Color = TogedyTheme.colors.gray800,
) {
    Canvas(modifier = modifier.padding(4.dp)) {
        val stroke = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Butt)
        val brush = Brush.sweepGradient(colors = gradientColors, center = Offset.Unspecified)

        drawArc(
            color = backgroundColor,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            style = stroke
        )

        drawArc(
            brush = brush,
            startAngle = -90f,
            sweepAngle = 360 * progress,
            useCenter = false,
            style = stroke
        )
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    TogedyTheme {
        Column {
            TimerSection(timerInfo = TimerInfo.mock1)
            TimerSection(timerInfo = TimerInfo.mock2)
        }
    }
}
