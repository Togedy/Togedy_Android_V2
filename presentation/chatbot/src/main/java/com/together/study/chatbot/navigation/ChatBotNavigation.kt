package com.together.study.chatbot.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.together.study.chatbot.ChatBotRoute
import com.together.study.common.navigation.MainTabRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToChatBot(
    navOptions: NavOptions? = null,
) = navigate(ChatBot)

fun NavGraphBuilder.chatBotGraph(
    navigateToUp: () -> Unit,
    onChatModeChanged: (Boolean) -> Unit,
    onRequestExit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<ChatBot> {
        ChatBotRoute(
            onNavigateBack = navigateToUp,
            onChatModeChanged = onChatModeChanged,
            onRequestExit = onRequestExit,
            modifier = modifier,
        )
    }
}

@Serializable
data object ChatBot : MainTabRoute
