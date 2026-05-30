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
internal fun TermsOfServiceRoute(
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit,
    viewModel: TermsOfServiceViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getTermsOfService()
    }

    when (uiState) {
        is UiState.Loading -> TogedyLoadingScreen()
        is UiState.Success -> MarkdownScreen(
            modifier = modifier,
            title = "서비스 이용약관",
            content = (uiState as UiState.Success<String>).data,
            onBackButtonClick = onBackButtonClick,
        )
        is UiState.Failure -> {}
        is UiState.Empty -> {}
    }
}

