package com.together.study.mypage.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.material3.RichText
import com.together.study.designsystem.R.drawable.ic_left_chevron
import com.together.study.designsystem.component.topbar.TogedyTopBar
import com.together.study.designsystem.theme.TogedyTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun MarkdownScreen(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
    onBackButtonClick: () -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.gray50)
            .padding(top = 14.dp),
    ) {
        stickyHeader {
            TogedyTopBar(
                title = title,
                leftIcon = ImageVector.vectorResource(id = ic_left_chevron),
                onLeftClicked = onBackButtonClick,
                modifier = Modifier
                    .background(TogedyTheme.colors.gray50),
            )

            Spacer(Modifier.height(10.dp))
        }

        item {
            val typography = TogedyTheme.typography

            CompositionLocalProvider(
                LocalTextStyle provides LocalTextStyle.current.copy(
                    fontFamily = typography.body14m.fontFamily,
                    fontSize = typography.body14m.fontSize,
                )
            ) {
                RichText(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 20.dp),
                ) {
                    Markdown(content = content)
                }
            }
        }
    }
}
