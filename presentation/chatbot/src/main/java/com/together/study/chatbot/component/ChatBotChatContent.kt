package com.together.study.chatbot.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.together.study.chatbot.model.ChatMessage
import com.together.study.designsystem.theme.TogedyTheme

@Composable
internal fun ChatBotChatContent(
    messages: List<ChatMessage>,
    listState: LazyListState,
    modifier: Modifier = Modifier,
) {
    // 새 메시지에만 애니메이션 적용
    var displayedMessageIds by rememberSaveable { mutableStateOf(setOf<String>()) }

    LaunchedEffect(messages) {
        displayedMessageIds = displayedMessageIds + messages.map { it.id }.toSet()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            text = "2025.11.19 수",
            style = TogedyTheme.typography.body13m,
            color = TogedyTheme.colors.gray700,
            modifier = Modifier
                .padding(top = 30.dp, bottom = 15.dp)
                .background(
                    color = TogedyTheme.colors.gray700.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(17.dp)
                )
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .align(Alignment.CenterHorizontally)
        )

        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.Bottom,
            contentPadding = PaddingValues(bottom = 8.dp),
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 20.dp),
        ) {
            itemsIndexed(
                items = messages,
                key = { _, item -> item.id },
            ) { index, chatMessage ->
                val isNewMessage = chatMessage.id !in displayedMessageIds
                AnimatedChatItem(
                    chatMessage = chatMessage,
                    shouldAnimate = isNewMessage,
                    modifier = if (index == 0) Modifier.padding(top = 15.dp) else Modifier,
                )
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun AnimatedChatItem(
    chatMessage: ChatMessage,
    shouldAnimate: Boolean,
    modifier: Modifier = Modifier,
) {
    var visible by remember { mutableStateOf(!shouldAnimate) }

    LaunchedEffect(chatMessage.id) {
        if (shouldAnimate) {
            visible = true
        }
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(300)) +
                slideInVertically(
                    animationSpec = tween(300),
                    initialOffsetY = { it / 2 }
                ),
        modifier = modifier,
    ) {
        ChatItem(
            message = if (chatMessage.isMine) chatMessage.message else chatMessage.displayedText,
            isMine = chatMessage.isMine,
            hasIcon = !chatMessage.isMine,
            textColor = if (chatMessage.isLoading) TogedyTheme.colors.gray500 else null,
        )
    }
}
