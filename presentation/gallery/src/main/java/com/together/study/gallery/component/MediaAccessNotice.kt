package com.together.study.gallery.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

// 선택한 사진만 허용 시 표시 문구
@Composable
internal fun PartialAccessBanner(
    onManageClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(TogedyTheme.colors.gray100)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = "선택한 사진만 볼 수 있어요",
            style = TogedyTheme.typography.body12m,
            color = TogedyTheme.colors.gray700,
        )

        Text(
            text = "사진 더 선택",
            style = TogedyTheme.typography.body12m,
            color = TogedyTheme.colors.green,
            modifier = Modifier.noRippleClickable(onManageClick),
        )
    }
}

// 허용 안함 시 표시 문구
@Composable
internal fun MediaAccessDeniedContent(
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "사진을 불러오려면\n사진 접근 권한이 필요해요",
            style = TogedyTheme.typography.body14m,
            color = TogedyTheme.colors.gray700,
            textAlign = TextAlign.Center,
        )

        Text(
            text = "설정에서 권한 허용하기",
            style = TogedyTheme.typography.body14m,
            color = TogedyTheme.colors.green,
            modifier = Modifier
                .padding(top = 12.dp)
                .noRippleClickable(onSettingsClick),
        )
    }
}

@Preview
@Composable
private fun MediaAccessNoticePreview() {
    TogedyTheme {
        Column {
            PartialAccessBanner(onManageClick = {})
            MediaAccessDeniedContent(onSettingsClick = {})
        }
    }
}
