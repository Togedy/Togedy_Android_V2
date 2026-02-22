package com.together.study.chatbot.component

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 30.dp),
    ) {
        Text(
            text = "2025.11.19 수",
            style = TogedyTheme.typography.body13m,
            color = TogedyTheme.colors.gray700,
            modifier = Modifier
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
            contentPadding = PaddingValues(top = 30.dp, bottom = 8.dp),
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 20.dp),
        ) {
            items(messages) { chatMessage ->
                ChatItem(
                    message = chatMessage.message,
                    isMine = chatMessage.isMine,
                    hasIcon = !chatMessage.isMine,
                    textColor = if (chatMessage.isLoading) TogedyTheme.colors.gray500 else null,
                )
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}
