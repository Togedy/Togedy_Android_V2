package com.together.study.designsystem.component.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

/**
 * 토글 버튼
 *
 * @param isToggleOn 토글 체크 여부
 * @param onToggleClick 토글 클릭 시 실행되는 콜백
 * @param modifier 수정자
 */
@Composable
fun TogedyToggleButton(
    isToggleOn: Boolean,
    onToggleClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val toggleWidth = 32.dp
    val toggleHeight = 20.dp
    val padding = 2.dp
    val thumbSize = toggleHeight - padding * 2

    val offsetX by animateDpAsState(
        targetValue = if (isToggleOn) toggleWidth - thumbSize - padding * 2 else 0.dp,
        label = "thumb_offset",
    )

    val backgroundColor by animateColorAsState(
        targetValue = if (isToggleOn) TogedyTheme.colors.green else TogedyTheme.colors.gray300,
        label = "bg_color"
    )

    Box(
        modifier = modifier
            .size(toggleWidth, toggleHeight)
            .clip(RoundedCornerShape(50))
            .background(backgroundColor)
            .noRippleClickable(onToggleClick)
            .padding(padding),
        contentAlignment = Alignment.CenterStart,
    ) {
        Box(
            modifier = Modifier
                .size(thumbSize)
                .offset(x = offsetX)
                .background(TogedyTheme.colors.white, RoundedCornerShape(50))
        )
    }
}

@Preview
@Composable
private fun TogedyToggleButtonPreview(modifier: Modifier = Modifier) {
    var isToggleOn by remember { mutableStateOf(false) }
    TogedyTheme {
        TogedyToggleButton(
            isToggleOn = isToggleOn,
            onToggleClick = { isToggleOn = !isToggleOn },
            modifier = modifier,
        )
    }
}