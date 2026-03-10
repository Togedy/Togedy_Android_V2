package com.together.study.mypage.ui.settings

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.together.study.designsystem.R.drawable.ic_left_chevron
import com.together.study.designsystem.component.topbar.TogedyTopBar
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.mypage.component.NoticeTitleItem

@Composable
internal fun NoticeMainRoute(
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit,
    onNoticeDetailNavigate: (Long) -> Unit,
    viewModel: NoticeMainViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
//        viewModel.getNoticeList()
    }

    NoticeMainScreen(
        modifier = modifier,
        onBackButtonClick = onBackButtonClick,
        onNoticeClick = onNoticeDetailNavigate,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun NoticeMainScreen(
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit,
    onNoticeClick: (Long) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.gray50)
            .padding(top = 14.dp),
    ) {
        stickyHeader {
            TogedyTopBar(
                title = "공지사항",
                leftIcon = ImageVector.vectorResource(id = ic_left_chevron),
                onLeftClicked = onBackButtonClick,
            )
        }

        item {
            Spacer(Modifier.height(4.dp))
        }

        item {
            NoticeTitleItem(
                title = "[이벤트] 후기왕을 찾아요 당첨자 발표",
                date = "2026.01.01",
                isNew = true,
                onItemClick = { onNoticeClick(0) },
            )

            NoticeTitleItem(
                title = "[업데이트] 오류 발생으로 인한 그룹 실시간 측정으로 발생한 오류",
                date = "2026.01.01",
                isNew = false,
                onItemClick = { onNoticeClick(0) },
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
            onBackButtonClick = {},
            onNoticeClick = {},
        )
    }
}
