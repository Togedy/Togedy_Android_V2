package com.together.study.designsystem.component.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.R.drawable.ic_left_chevron
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

@Composable
fun TogedyTopBar(
    title: String? = null,
    titleStyle: TextStyle = TogedyTheme.typography.title16sb.copy(color = TogedyTheme.colors.black),
    leftIcon: ImageVector? = null,
    leftIconColor: Color = TogedyTheme.colors.black,
    rightText: String? = null,
    rightTextStyle: TextStyle = TogedyTheme.typography.title16sb.copy(color = TogedyTheme.colors.black),
    onLeftClicked: () -> Unit = {},
    onRightClicked: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        if (title != null) {
            Text(
                text = title,
                style = titleStyle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 60.dp)
            )
        }

        if (leftIcon != null) {
            Icon(
                imageVector = leftIcon,
                contentDescription = "left",
                tint = leftIconColor,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .noRippleClickable(onLeftClicked)
                    .padding(horizontal = 16.dp)
            )
        }

        if (rightText != null) {
            Text(
                text = rightText,
                style = rightTextStyle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .noRippleClickable(onRightClicked)
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

@Preview()
@Composable
private fun TopBarPreview() {
    TogedyTheme {
        Box(
            modifier = Modifier
                .background(TogedyTheme.colors.white)
                .fillMaxWidth()
        ) {
            TogedyTopBar(
                title = "아주 아주 길어진 스터디 생성 타이틀이 중앙에서 말줄임표로 처리되는지 확인",
                leftIcon = ImageVector.vectorResource(id = ic_left_chevron),
                rightText = "편집",
                rightTextStyle = TogedyTheme.typography.title16sb.copy(
                    color = TogedyTheme.colors.green
                )
            )
        }
    }
}