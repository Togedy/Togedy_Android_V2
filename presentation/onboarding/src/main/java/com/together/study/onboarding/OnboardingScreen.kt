package com.together.study.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.together.study.designsystem.R.drawable.ic_left_chevron_green
import com.together.study.designsystem.component.toast.LocalTogedyToast
import com.together.study.designsystem.component.toast.ToastType
import com.together.study.designsystem.component.topbar.TogedyTopBar
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.onboarding.state.OnboardingUiEvent
import com.together.study.onboarding.state.OnboardingUiState
import kotlinx.coroutines.launch

@Composable
internal fun OnboardingRoute(
    onNavigateToCalendar: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val togedyToast = LocalTogedyToast.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is OnboardingUiEvent.NavigateToCalendar -> {
                    onNavigateToCalendar()
                }
                is OnboardingUiEvent.ShowError -> {
                    togedyToast.makeText(
                        toastType = ToastType.COMMON,
                        message = event.message,
                    )
                }
            }
        }
    }

    OnboardingScreen(
        uiState = uiState,
        onNicknameChange = viewModel::updateNickname,
        onValidateNickname = viewModel::validateNickname,
        onCompleteOnboarding = viewModel::completeOnboarding,
        modifier = modifier,
    )
}

@Composable
internal fun OnboardingScreen(
    uiState: OnboardingUiState = OnboardingUiState(),
    onNicknameChange: (String) -> Unit = {},
    onValidateNickname: () -> Unit = {},
    onCompleteOnboarding: (java.time.LocalDate) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(
        pageCount = { 2 }
    )
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.gray100)
            .statusBarsPadding()
    ) {
        TogedyTopBar(
            modifier = Modifier.fillMaxWidth(),
            leftIcon = ImageVector.vectorResource(ic_left_chevron_green),
            leftIconColor = TogedyTheme.colors.gray800,
            onLeftClicked = {
                if (pagerState.currentPage > 0) {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(30.dp))

        OnboardingProgressBar(
            currentPage = pagerState.currentPage,
            pageCount = pagerState.pageCount,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(25.dp))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            userScrollEnabled = false,
        ) { page ->
            when (page) {
                0 -> OnboardingNicknameScreen(
                    nickname = uiState.nickname,
                    isNicknameValidated = uiState.isNicknameValidated,
                    nicknameErrorMessage = uiState.nicknameErrorMessage,
                    isValidatingNickname = uiState.isValidatingNickname,
                    onNicknameChange = onNicknameChange,
                    onValidateNickname = onValidateNickname,
                    onNextClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(1)
                        }
                    },
                )

                1 -> OnboardingBirthScreen(
                    isSubmitting = uiState.isSubmitting,
                    onCompleteClick = onCompleteOnboarding,
                )
            }
        }
    }
}

@Composable
private fun OnboardingProgressBar(
    currentPage: Int,
    pageCount: Int,
    modifier: Modifier = Modifier,
) {
    val progress = (currentPage + 1) / pageCount.toFloat()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(4.dp)
            .clip(RoundedCornerShape(100.dp))
            .background(TogedyTheme.colors.gray200)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress)
                .fillMaxSize()
                .clip(RoundedCornerShape(100.dp))
                .background(TogedyTheme.colors.green)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun OnboardingScreenPreview() {
    TogedyTheme {
        OnboardingScreen()
    }
}
