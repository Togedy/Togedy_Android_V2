package com.together.study.mypage.ui.account

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.R
import com.together.study.designsystem.R.drawable.ic_left_chevron
import com.together.study.designsystem.component.button.TogedyBasicButton
import com.together.study.designsystem.component.button.TogedyButton
import com.together.study.designsystem.component.topbar.TogedyTopBar
import com.together.study.designsystem.theme.TogedyTheme

private const val FLOAT_ANIMATION_DURATION = 1200
private const val FLOAT_OFFSET_START = -6f
private const val FLOAT_OFFSET_END = 6f

@Composable
internal fun DeleteAccountScreen(
    userName: String,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "float")

    val offsetY by infiniteTransition.animateFloat(
        initialValue = FLOAT_OFFSET_START,
        targetValue = FLOAT_OFFSET_END,
        animationSpec = infiniteRepeatable(
            animation = tween(FLOAT_ANIMATION_DURATION),
            repeatMode = RepeatMode.Reverse
        ),
        label = "floatY"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.gray50)
            .statusBarsPadding()
            .padding(bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TogedyTopBar(
            title = "탈퇴하기",
            leftIcon = ImageVector.vectorResource(id = ic_left_chevron),
            onLeftClicked = onBackClick,
            modifier = Modifier.padding(vertical = 10.dp),
        )

        Spacer(Modifier.weight(1f))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_character_crying),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .offset(y = offsetY.dp),
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = "${userName}님과의 이별인가요?",
                style = TogedyTheme.typography.title16sb,
            )

            Text(
                text = "잠깐! 입시요정의 불꽃이 꺼지려고 해요... \uD83D\uDD25 " +
                        "\n우리가 함께 뜨겁게 타오르며 " +
                        "\n공부했던 시간들을 잊으셨나요? " +
                        "\n\n당신의 꿈을 향한 열정, 여기서 포기하기엔 " +
                        "\n너무 아까워요!",
                style = TogedyTheme.typography.body14m,
                color = TogedyTheme.colors.gray600,
                textAlign = TextAlign.Center,
            )
        }

        Spacer(Modifier.weight(1f))

        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "잊지마세요! 탈퇴 버튼 선택시, 계정과 모든 데이터는 삭제되며  복구되지 않습니다",
                style = TogedyTheme.typography.body10m,
                color = TogedyTheme.colors.green,
            )

            Spacer(Modifier.height(16.dp))

            TogedyButton(
                text = "탈퇴하기",
                enabled = true,
                onClick = {
                    /* 서버 호출 */
                },
                modifier = Modifier,
            )

            TogedyBasicButton(
                title = "취소하기",
                onClick = onBackClick,
                containerColor = TogedyTheme.colors.gray50,
                contentColor = TogedyTheme.colors.gray500,
                modifier = Modifier,
            )
        }
    }
}

@Preview
@Composable
private fun DeleteAccountScreenPreview() {
    TogedyTheme {
        DeleteAccountScreen(
            userName = "투게디짱",
            onBackClick = {},
        )
    }
}
