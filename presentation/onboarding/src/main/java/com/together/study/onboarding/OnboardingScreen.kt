package com.together.study.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.R.drawable.ic_left_chevron_green
import com.together.study.designsystem.component.topbar.TogedyTopBar
import com.together.study.designsystem.theme.TogedyTheme
import kotlinx.coroutines.launch

@Composable
internal fun OnboardingScreen() {
    val pagerState = rememberPagerState(
        pageCount = { 2 }
    )
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.gray100)
            .padding(top = 23.dp)
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
        ) { page ->
            when (page) {
                0 -> OnboardingNicknameScreen(
                    onNextClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(1)
                        }
                    },
                )

                1 -> OnboardingBirthScreen()
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
