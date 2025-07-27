package com.together.study.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.theme.TogedyTheme

@Composable
fun SearchSelectorChipAdded(
    addedList: List<String>
) {
    Box(
        modifier = Modifier
            .background(
                color = TogedyTheme.colors.gray100,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 4.dp, vertical = 1.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = buildAnnotatedString {
                addedList.forEachIndexed { index, item ->
                    withStyle(
                        style = SpanStyle(
                            color = TogedyTheme.colors.green
                        )
                    ) {
                        append(item)
                        if (index != addedList.lastIndex) {
                            append(",")
                        }
                    }
                }
                withStyle(
                    style = SpanStyle(
                        color = TogedyTheme.colors.gray500
                    )
                ) {
                    append("을 담아두었어요")
                }
            },
            style = TogedyTheme.typography.body12m
        )
    }
}