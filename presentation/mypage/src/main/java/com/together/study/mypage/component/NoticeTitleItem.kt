package com.together.study.mypage.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.together.study.designsystem.R.drawable.ic_new_badge
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

@Composable
internal fun NoticeTitleItem(
    title: String,
    date: String,
    isNew: Boolean,
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .noRippleClickable(onItemClick)
            .fillMaxWidth()
            .padding(top = 16.dp)
            .padding(horizontal = 20.dp),
    ) {
        if (isNew) {
            TextWithNewIcon(
                text = title,
            )
        } else {
            Text(
                text = title,
                style = TogedyTheme.typography.body14m,
                color = TogedyTheme.colors.gray800,
            )
        }

        Spacer(Modifier.height(4.dp))

        Text(
            text = date,
            style = TogedyTheme.typography.body12m,
            color = TogedyTheme.colors.gray500,
        )
        Spacer(Modifier.height(8.dp))

        HorizontalDivider(color = TogedyTheme.colors.gray200)
    }
}


@Composable
private fun TextWithNewIcon(text: String) {
    val inlineContent = mapOf(
        "newIcon" to InlineTextContent(
            Placeholder(
                width = 16.sp,
                height = 16.sp,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center,
            )
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(ic_new_badge),
                contentDescription = null,
                tint = Color.Unspecified,
            )
        }
    )

    Text(
        text = buildAnnotatedString {
            append(text)
            append(" ")
            appendInlineContent("newIcon")
        },
        inlineContent = inlineContent,
        style = TogedyTheme.typography.body14m,
        color = TogedyTheme.colors.gray800,
    )
}

@Preview
@Composable
private fun NoticeTitleItemPreview() {
    TogedyTheme {
        NoticeTitleItem(
            title = "[이벤트] 후기왕을 찾아요 당첨자 발표",
            date = "2026.01.01",
            isNew = true,
            onItemClick = {},
        )
    }
}