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
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.together.study.designsystem.R.drawable.ic_left_chevron
import com.together.study.designsystem.component.loading.TogedyLoadingScreen
import com.together.study.designsystem.component.topbar.TogedyTopBar
import com.together.study.designsystem.theme.SystemBarIcons
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.mypage.component.NoticeTitleItem
import com.together.study.mypage.model.Notice

@Composable
internal fun NoticeMainRoute(
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit,
    onNoticeDetailNavigate: (Long) -> Unit,
    viewModel: NoticeMainViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getNoticeList()
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
            leftIcon = ImageVector.vectorResource(id = ic_left_chevron),
            onLeftClicked = onBackButtonClick,
        )

        when (uiState.value) {
            is UiState.Loading -> TogedyLoadingScreen()
            is UiState.Failure -> {}

            is UiState.Success ->
                NoticeMainScreen(
                    notices = (uiState.value as UiState.Success<List<Notice>>).data,
                    modifier = modifier,
                    onNoticeClick = onNoticeDetailNavigate,
                )

            is UiState.Empty -> {}
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun NoticeMainScreen(
    notices: List<Notice>,
    modifier: Modifier = Modifier,
    onNoticeClick: (Long) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        item {
            Spacer(Modifier.height(4.dp))
        }

        itemsIndexed(notices) { index, notice ->
            NoticeTitleItem(
                title = notice.noticeTitle,
                date = notice.publishedAt,
                isNew = false,
                onItemClick = { notice.noticeId?.let { onNoticeClick(it) } },
            )
        }

        item {
            Spacer(Modifier.height(30.dp))
        }
    }
}

@Preview
@Composable
private fun NoticeMainScreenPreview() {
    TogedyTheme {
        NoticeMainScreen(
            notices = listOf(
                Notice(noticeId = 1, noticeTitle = "공지사항 제목 1", publishedAt = "2024-06-01"),
                Notice(noticeId = 2, noticeTitle = "공지사항 제목 2", publishedAt = "2024-06-02"),
                Notice(noticeId = 3, noticeTitle = "공지사항 제목 3", publishedAt = "2024-06-03"),
            ),
            onNoticeClick = {},
        )
    }
}
