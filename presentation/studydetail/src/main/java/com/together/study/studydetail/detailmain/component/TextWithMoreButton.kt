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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.together.study.designsystem.theme.TogedyTheme

private const val SEE_MORE_TEXT = "...더보기"
private const val SEE_LESS_TEXT = " ...간략히"
private const val ELLIPSIS_WIDTH_IN_CHARS = 8

@Composable
fun TextWithMoreButton(
    text: String,
    maxLines: Int = 4,
    modifier: Modifier = Modifier,
) {
    var isExpanded by remember { mutableStateOf(false) }
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }

    val contentColor = TogedyTheme.colors.gray600
    val buttonColor = TogedyTheme.colors.green

    val isTextClipped = textLayoutResult?.didOverflowHeight ?: false
    val showSeeMoreButton = isTextClipped && !isExpanded

    val onTextClick = {
        if (isTextClipped || isExpanded) {
            isExpanded = !isExpanded
        }
    }

    Box(
        modifier = modifier
            .clickable(onClick = onTextClick)
            .clipToBounds(),
    ) {
        val annotatedText = createAnnotatedText(
            text = text,
            isExpanded = isExpanded,
            showSeeMoreButton = showSeeMoreButton,
            textLayoutResult = textLayoutResult,
            contentColor = contentColor,
            buttonColor = buttonColor,
            maxLines = maxLines,
        )

        Text(
            text = annotatedText,
            style = TogedyTheme.typography.body14m,
            maxLines = if (isExpanded) Int.MAX_VALUE else maxLines,
            onTextLayout = { result ->
                if (textLayoutResult == null || result.lineCount != textLayoutResult?.lineCount) {
                    textLayoutResult = result
                }
            },
        )
    }
}

@Composable
private fun createAnnotatedText(
    text: String,
    isExpanded: Boolean,
    showSeeMoreButton: Boolean,
    textLayoutResult: TextLayoutResult?,
    contentColor: Color,
    buttonColor: Color,
    maxLines: Int,
): AnnotatedString = buildAnnotatedString {

    val contentStyle = SpanStyle(contentColor)
    val buttonStyle = SpanStyle(buttonColor)

    when {
        showSeeMoreButton -> {
            val lastLineIndex = maxLines - 1
            textLayoutResult?.let { layoutResult ->
                if (layoutResult.lineCount > lastLineIndex) {
                    val lineEndIndex = layoutResult.getLineEnd(lastLineIndex, visibleEnd = true)
                    val effectiveTextEnd = (lineEndIndex - ELLIPSIS_WIDTH_IN_CHARS).coerceAtLeast(0)

                    withStyle(contentStyle) {
                        append(text.substring(0, effectiveTextEnd))
                    }
                } else {
                    withStyle(contentStyle) {
                        append(text)
                    }
                }
            }

            withStyle(buttonStyle) {
                append(SEE_MORE_TEXT)
            }
        }

        isExpanded -> {
            withStyle(contentStyle) {
                append(text)
            }
            withStyle(buttonStyle) {
                append(SEE_LESS_TEXT)
            }
        }

        else -> {
            withStyle(contentStyle) {
                append(text)
            }
        }
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