package com.together.study.designsystem.component.sheet

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

@Composable
fun TogedyTopSheet(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    rightButton: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    if (!visible) return

    val offsetY by animateDpAsState(
        targetValue = if (visible) 0.dp else (-400).dp,
        label = "TopSheetOffset",
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(1f),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(TogedyTheme.colors.black.copy(alpha = 0.4f))
                .noRippleClickable(onDismissRequest)
        )

        Column(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .offset(y = offsetY)
                .background(
                    TogedyTheme.colors.white,
                    RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                )
                .padding(top = 40.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd,
            ) {
                title()

                rightButton()
            }

            content()
        }
    }
}
