package com.together.study.chatbot

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.together.study.chatbot.component.ChatBotChatContent
import com.together.study.chatbot.component.ChatBotIntroContent
import com.together.study.chatbot.component.ChatInputBar
import com.together.study.designsystem.theme.TogedyTheme

@Composable
internal fun ChatBotRoute(
    onNavigateBack: () -> Unit,
    onChatModeChanged: (Boolean) -> Unit,
    onRequestExit: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ChatBotViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isChatMode) {
        onChatModeChanged(uiState.isChatMode)
    }

    ChatBotScreen(
        uiState = uiState,
        onInputTextChange = viewModel::updateInputText,
        onSendMessage = viewModel::sendMessage,
        onRequestExit = onRequestExit,
        modifier = modifier,
    )
}

@Composable
internal fun ChatBotScreen(
    uiState: ChatBotUiState,
    onInputTextChange: (String) -> Unit,
    onSendMessage: (String) -> Unit,
    onRequestExit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }
    var visible by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()

    BackHandler(enabled = uiState.isChatMode) {
        onRequestExit()
    }

    LaunchedEffect(Unit) {
        visible = true
    }

    LaunchedEffect(uiState.messages.size) {
        if (uiState.messages.isNotEmpty()) {
            listState.animateScrollToItem(uiState.messages.size - 1)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(TogedyTheme.colors.green500, TogedyTheme.colors.gray100),
                    center = Offset(x = screenWidthPx / 2, y = 160f),
                    radius = screenWidthPx * 0.6f,
                ),
            )
            .windowInsetsPadding(WindowInsets.ime)
            .windowInsetsPadding(WindowInsets.navigationBars),
    ) {
        // 초기 화면 / 채팅 화면 영역
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.TopCenter,
        ) {
            // 초기 화면
            androidx.compose.animation.AnimatedVisibility(
                visible = visible && !uiState.isChatMode,
                enter = fadeIn(animationSpec = tween(1200)) +
                        slideInVertically(initialOffsetY = { -40 }),
                exit = fadeOut(animationSpec = tween(400)) +
                        slideOutVertically(
                            animationSpec = tween(400),
                            targetOffsetY = { -it }
                        ),
            ) {
                ChatBotIntroContent(
                    onQuestionClick = onSendMessage,
                )
            }

            // 채팅 화면
            androidx.compose.animation.AnimatedVisibility(
                visible = uiState.isChatMode,
                enter = fadeIn(animationSpec = tween(500)) +
                        slideInVertically(
                            animationSpec = tween(500),
                            initialOffsetY = { it }
                        ),
                exit = fadeOut(animationSpec = tween(300)),
                modifier = Modifier.fillMaxSize(),
            ) {
                ChatBotChatContent(
                    messages = uiState.messages,
                    listState = listState,
                )
            }
        }

        // 하단 입력창
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(900)) +
                    slideInVertically(initialOffsetY = { it / 2 }),
        ) {
            ChatInputBar(
                value = uiState.inputText,
                placeholderText = "입시에 대해 궁금한 것을 물어보세요",
                onValueChange = onInputTextChange,
                onSendClick = { onSendMessage(uiState.inputText) },
                isSendAvailable = !uiState.isWaitingResponse,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}