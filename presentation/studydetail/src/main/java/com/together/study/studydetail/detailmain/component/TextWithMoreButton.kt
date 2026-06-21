package com.together.study.studydetail.detailmain.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
    var truncateIndex by remember { mutableIntStateOf(-1) }

    val contentColor = TogedyTheme.colors.gray600
    val buttonColor = TogedyTheme.colors.green

    val hasOverflow = truncateIndex >= 0
    val isMeasured = truncateIndex >= 0 || truncateIndex == -2

    Box(
        modifier = modifier
            .clickable {
                if (hasOverflow || isExpanded) {
                    isExpanded = !isExpanded
                }
            }
            .clipToBounds(),
    ) {
        if (!isMeasured && !isExpanded) {
            // 측정 및 짧을 경우 텍스트
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(contentColor)) { append(text) }
                },
                style = TogedyTheme.typography.body14m,
                maxLines = maxLines,
                onTextLayout = { result ->
                    if (result.hasVisualOverflow) {
                        val lastLineIndex = maxLines - 1
                        if (result.lineCount > lastLineIndex) {
                            val lineEndIndex = result.getLineEnd(lastLineIndex, visibleEnd = true)
                            truncateIndex =
                                (lineEndIndex - ELLIPSIS_WIDTH_IN_CHARS).coerceAtLeast(0)
                        } else {
                            truncateIndex = -2
                        }
                    } else {
                        // overflow 없음 → 더보기 불필요
                        truncateIndex = -2
                    }
                },
            )
        } else {
            // 측정 완료 후 텍스트
            val displayText = createDisplayText(
                text = text,
                isExpanded = isExpanded,
                hasOverflow = hasOverflow,
                truncateIndex = truncateIndex,
                contentColor = contentColor,
                buttonColor = buttonColor,
            )

            Text(
                text = displayText,
                style = TogedyTheme.typography.body14m,
                maxLines = if (isExpanded) Int.MAX_VALUE else maxLines,
            )
        }
    }
}

private fun createDisplayText(
    text: String,
    isExpanded: Boolean,
    hasOverflow: Boolean,
    truncateIndex: Int,
    contentColor: Color,
    buttonColor: Color,
): AnnotatedString = buildAnnotatedString {
    val contentStyle = SpanStyle(contentColor)
    val buttonStyle = SpanStyle(buttonColor)

    when {
        hasOverflow && !isExpanded -> {
            withStyle(contentStyle) {
                append(text.substring(0, truncateIndex))
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
