package com.together.study.mypage.ui.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.together.study.common.state.UiState
import com.together.study.designsystem.component.loading.TogedyLoadingScreen
import com.together.study.mypage.component.MarkdownScreen

@Composable
internal fun PrivacyPolicyRoute(
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit,
    viewModel: PrivacyPolicyViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getPrivacyPolicy()
    }

    when (uiState) {
        is UiState.Loading -> TogedyLoadingScreen()
        is UiState.Success -> MarkdownScreen(
            modifier = modifier,
            title = "개인정보처리방침",
            content = (uiState as UiState.Success<String>).data,
            onBackButtonClick = onBackButtonClick,
        )

        is UiState.Failure -> {}
        is UiState.Empty -> {}
    }
}