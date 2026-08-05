package com.together.study.login.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.together.study.designsystem.theme.SystemBarIcons
import com.together.study.designsystem.theme.TogedyTheme

@Composable
internal fun SplashRoute(
    onNavigateToLogin: () -> Unit,
    onNavigateToCalendar: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is SplashUiEvent.NavigateToLogin -> onNavigateToLogin()
                is SplashUiEvent.NavigateToCalendar -> onNavigateToCalendar()
            }
        }
    }

    SplashScreen()
}

@Composable
internal fun SplashScreen(
    modifier: Modifier = Modifier,
) {
    SystemBarIcons()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = TogedyTheme.colors.white),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Togedy",
            style = TogedyTheme.typography.title24b.copy(
                color = TogedyTheme.colors.green
            )
        )
    }
}
