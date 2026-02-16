package com.together.study.chatbot

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.together.study.chatbot.component.ChatInputBar
import com.together.study.chatbot.component.ChatItem
import com.together.study.designsystem.R.drawable.img_character_heart
import com.together.study.designsystem.theme.TogedyTheme

@Composable
fun ChatBotRoute(modifier: Modifier = Modifier) {
    ChatBotScreen(
        modifier = modifier,
    )
}

@Composable
fun ChatBotScreen(
    modifier: Modifier = Modifier,
) {
    val configuration = LocalConfiguration.current
    val screenWidthPx = with(LocalDensity.current) { configuration.screenWidthDp.dp.toPx() }
    var visible by remember { mutableStateOf(false) }
    var inputText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        visible = true
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(TogedyTheme.colors.green500, TogedyTheme.colors.gray100),
                    center = Offset(x = screenWidthPx / 2, y = 160f),
                    radius = screenWidthPx * 0.6f,
                ),
            ),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(120.dp))

            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(animationSpec = tween(1200)) +
                        slideInVertically(initialOffsetY = { -40 }),
            ) {
                Column {
                    Image(
                        painter = painterResource(img_character_heart),
                        contentDescription = null,
                    )

                    Text(
                        text = "입시에 대한",
                        style = TogedyTheme.typography.title24b,
                        color = TogedyTheme.colors.gray700,
                        modifier = Modifier.padding(top = 10.dp, start = 10.dp),
                    )
                    Text(
                        text = "모든 것을 물어보세요!",
                        style = TogedyTheme.typography.title24b,
                        color = TogedyTheme.colors.gray700,
                        modifier = Modifier.padding(start = 10.dp, bottom = 28.dp),
                    )

                    ChatItem(
                        message = "수능까지 몇 일 남았나요?",
                        isMine = false,
                    )
                    Spacer(Modifier.height(6.dp))
                    ChatItem(
                        message = "이번 주 원서 접수하는 대학이 어디인가요?",
                        isMine = false,
                    )
                    Spacer(Modifier.height(6.dp))
                    ChatItem(
                        message = "제게 맞는 공부법을 알고 싶어요",
                        isMine = false,
                    )
                    Spacer(Modifier.height(6.dp))
                    ChatItem(
                        message = "건국대학교 편입 원서 접수 기간이 궁금해요",
                        isMine = false,
                    )
                }

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                ) {
//                        Text(
//                            text = "2025.11.19 수",
//                            style = TogedyTheme.typography.body13m,
//                            color = TogedyTheme.colors.gray700,
//                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp)
//                        )
                }
            }
        }

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(900)) +
                    slideInVertically(initialOffsetY = { it / 2 }),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .imePadding(),
        ) {
            ChatInputBar(
                value = inputText,
                placeholderText = "입시에 대해 궁금한 것을 물어보세요",
                onValueChange = { inputText = it },
                onSendClick = { inputText = "" },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}