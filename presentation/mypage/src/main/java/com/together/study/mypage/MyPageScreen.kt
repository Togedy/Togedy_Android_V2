package com.together.study.mypage

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.together.study.designsystem.theme.TogedyTheme


@Composable
internal fun MyPageRoute(
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    MyPageScreen()
}

@Composable
private fun MyPageScreen(
    modifier: Modifier = Modifier,
) {

}

@Preview
@Composable
private fun MyPageRoutePreview() {
    TogedyTheme {
        MyPageScreen()
    }
}