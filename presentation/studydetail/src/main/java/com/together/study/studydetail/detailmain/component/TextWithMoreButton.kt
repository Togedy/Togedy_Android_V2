package com.together.study.studydetail.detailmain.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.together.study.designsystem.theme.TogedyTheme

@Composable
fun TextWithMoreButton(
    text: String,
    maxLines: Int = 4,
    modifier: Modifier = Modifier,
) {
    var isExpanded by remember { mutableStateOf(false) }
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
    val isTextClipped = textLayoutResult?.didOverflowHeight ?: false
    val showSeeMore = isTextClipped && !isExpanded

    Box(
        modifier = modifier
            .clickable { isExpanded = !isExpanded }
            .clipToBounds(),
    ) {
        val annotatedText =
            if (showSeeMore) {
                val lastCharIndex =
                    textLayoutResult?.getLineEnd(maxLines - 1, visibleEnd = true)
                        ?: text.length

                val effectiveText = text.take(lastCharIndex)

                buildAnnotatedString {
                    withStyle(SpanStyle(TogedyTheme.colors.gray600)) {
                        append(effectiveText.dropLast("...더보기".length))
                    }
                    withStyle(SpanStyle(TogedyTheme.colors.green)) {
                        append("...더보기")
                    }
                }
            } else {
                if (isExpanded) {
                    buildAnnotatedString {
                        withStyle(SpanStyle(TogedyTheme.colors.gray600)) {
                            append(text)
                        }
                        withStyle(SpanStyle(TogedyTheme.colors.green)) {
                            append(" ...간략히")
                        }
                    }
                } else {
                    buildAnnotatedString {
                        withStyle(SpanStyle(TogedyTheme.colors.gray600)) {
                            append(text)
                        }
                    }
                }
            }

        Text(
            text = annotatedText,
            style = TogedyTheme.typography.body14m,
            maxLines = if (isExpanded) Int.MAX_VALUE else maxLines,
            onTextLayout = { result ->
                textLayoutResult = result
            },
        )
    }
}

@Preview
@Composable
private fun TextWithMoreButtonPreview() {
    TogedyTheme {
        TextWithMoreButton(
            text = "예시 텍스트입니다.예시 텍스트입니다.예시 텍스트입니다.예시 텍스트입니다.예시 텍스트입니다.예시 텍스트입니다.예시 텍스트입니다.예시 텍스트입니다.예시 텍스트입니다.예시 텍스트입니다.예시 텍스트입니다.예시 텍스트입니다.예시 텍스트입니다.예시 텍스트입니다.예시 텍스트입니다.예시 텍스트입니다.예시 텍스트입니다.예시 텍스트입니다.예시 텍스트입니다.예시 텍스트입니다."
        )
    }
}