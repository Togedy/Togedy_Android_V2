package com.together.study.chatbot.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

@Composable
internal fun ChatItem(
    message: String,
    isMine: Boolean,
    hasIcon: Boolean = false,
    isLoading: Boolean = false,
    textColor: Color? = null,
    onItemClick: () -> Unit = {},
) {
    val cornerShape =
        if (isMine) RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp)
        else RoundedCornerShape(topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp)
    val textColor =
        textColor ?: if (isMine) TogedyTheme.colors.black
        else TogedyTheme.colors.gray600
    val backgroundColor = if (isMine) Color(0x3300DF82) else TogedyTheme.colors.white
    val configuration = LocalConfiguration.current
    val maxChatWidth = (configuration.screenWidthDp * 0.8f).dp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .noRippleClickable(onItemClick),
        horizontalAlignment = if (isMine) Alignment.End else Alignment.Start,
    ) {
        // 임의 아이콘 비활성화, 추후 변경 논의
//        if (hasIcon) {
//            Image(
//                painter = painterResource(img_character_heart),
//                contentDescription = null,
//                modifier = Modifier.size(54.dp),
//            )
//
//            Spacer(Modifier.height(4.dp))
//        }

        Box(
            modifier = Modifier
                .widthIn(max = maxChatWidth)
                .background(
                    color = backgroundColor,
                    shape = cornerShape
                )
                .padding(horizontal = 16.dp, vertical = 12.dp),
        ) {
            if (isLoading) {
                AnimatedLoadingText(
                    text = message,
                    color = textColor,
                )
            } else {
                Text(
                    text = message,
                    style = TogedyTheme.typography.body13m,
                    color = textColor,
                )
            }
        }
    }
}

@Composable
private fun AnimatedLoadingText(
    text: String,
    color: Color,
) {
    val transition = rememberInfiniteTransition()

    Row {
        var dotIndex = 0
        text.forEach { char ->
            if (char == '.') {
                val currentDotIndex = dotIndex++
                val scale by transition.animateFloat(
                    initialValue = 1f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = keyframes {
                            durationMillis = 900
                            1f at 0
                            1.22f at 300 using FastOutSlowInEasing
                            1f at 600
                            1f at 900
                        },
                        initialStartOffset = StartOffset(currentDotIndex * 120),
                    ),
                )

                Text(
                    text = ".",
                    style = TogedyTheme.typography.body13m,
                    color = color,
                    modifier = Modifier.graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    },
                )
            } else {
                Text(
                    text = char.toString(),
                    style = TogedyTheme.typography.body13m,
                    color = color,
                )
            }
        }
    }
}

@Preview
@Composable
private fun MyChatItemPreview() {
    TogedyTheme {
        ChatItem(
            message = "수능까지 몇 일 남았나요?",
            isMine = true,
        )
    }
}

@Preview
@Composable
private fun AnswerChatItemPreview() {
    TogedyTheme {
        ChatItem(
            message = "000님, 안녕하세요! 건국대학교 2025학년도 편입학 원서 접수 기간에 대해 궁금해하시는군요.\n" +
                    "2025학년도 건국대학교 편입학 원서 접수는 이미 마감되었습니다. 접수 기간은 2024년 12월 11일(수) 09:30부터 2024년 12월 17일(화) 18:00까지 온라인으로 진행되었었습니다. \n" +
                    "더 자세한 정보는 건국대학교 입학처 홈페이지에서 2025학년도 편입학 모집요강을 확인해 보시는 것이 가장 정확합니다.",
            isMine = false,
            hasIcon = true,
        )
    }
}

@Preview
@Composable
private fun LoadingChatItemPreview() {
    TogedyTheme {
        ChatItem(
            message = "생각중...",
            isMine = false,
            hasIcon = true,
            isLoading = true,
            textColor = TogedyTheme.colors.gray500,
        )
    }
}