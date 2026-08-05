package com.together.study.mypage.ui.settings

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.together.study.common.state.UiState
import com.together.study.designsystem.R.drawable.ic_delete_x_16
import com.together.study.designsystem.component.loading.TogedyLoadingScreen
import com.together.study.designsystem.component.topbar.TogedyTopBar
import com.together.study.designsystem.theme.SystemBarIcons
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.mypage.component.NoticeTitleItem

@Composable
internal fun NoticeDetailRoute(
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit,
    viewModel: NoticeDetailViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getNoticeDetailInfo()
    }

    SystemBarIcons()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.gray50)
            .systemBarsPadding(),
    ) {
        TogedyTopBar(
            title = "공지사항",
            leftIcon = ImageVector.vectorResource(id = ic_delete_x_16),
            onLeftClicked = onBackButtonClick,
        )

        Spacer(Modifier.height(4.dp))

        when (uiState.value) {
            is UiState.Loading -> TogedyLoadingScreen()
            is UiState.Failure -> {}
            is UiState.Success -> {
                val notice = (uiState.value as UiState.Success).data

                NoticeDetailScreen(
                    title = notice.noticeTitle,
                    date = notice.publishedAt,
                    content = notice.noticeContent,
                    isNew = false,
                    modifier = modifier,
                )
            }

            is UiState.Empty -> {}
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun NoticeDetailScreen(
    modifier: Modifier = Modifier,
    title: String,
    date: String,
    content: String,
    isNew: Boolean,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        stickyHeader {
            NoticeTitleItem(
                title = title,
                date = date,
                isNew = isNew,
            )
        }

        item {
            Text(
                text = content,
                style = TogedyTheme.typography.body12m,
                color = TogedyTheme.colors.gray800,
                modifier = Modifier.padding(20.dp)
            )
        }

        item {
            Spacer(Modifier.height(10.dp))
        }
    }
}

@Preview
@Composable
private fun NoticeDetailScreenPreview() {
    TogedyTheme {
        NoticeDetailScreen(
            title = "[이벤트] 후기왕을 찾아요 당첨자 발표",
            date = "2026.01.01",
            content = "\uD83D\uDC9A5회차 후기왕 이벤트 당첨자 발표\uD83C\uDF89당첨되신 분들 모두 축하드립니다~!\uD83E\uDD73\n" +
                    "\n" +
                    "\uD83C\uDF81깜짝 선물\uD83C\uDF81\n" +
                    " \n" +
                    "\uD83D\uDC9B더 커진 리워드로 투게디가 돌아왔습니다\uD83D\uDC9B\n" +
                    "당첨되신 분들은 선물 수령 후\n" +
                    "타 커뮤니티, SNS에 공유해주세요~!\uD83D\uDE06\n" +
                    " \n" +
                    "PC에서 Ctrl + F 를 누른 후, 닉네임을 검색하면\n" +
                    "더 빠르게 닉네임을 찾으실 수 있습니다\uD83D\uDC97\n" +
                    " \n" +
                    "\uD83E\uDD73후기왕 이벤트에 당첨되신 분들 모두 축하드립니다\uD83E\uDD73\n" +
                    "상품은 참여 인증 설문지에 적어주신 연락처로 발송됩니다.\n" +
                    "\n" +
                    "\n" +
                    "감사합니다. 여러분의 성원에 힘입어 더 멋진 서비스로 보답하겠습니다.",
            isNew = true,
        )
    }
}