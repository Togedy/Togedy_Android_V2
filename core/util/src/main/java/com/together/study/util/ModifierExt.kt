package com.together.study.util

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer

/**
 * Material3의 기본 리플 모션을 제거하기 위한 함수입니다.
 */
@SuppressLint("ModifierFactoryUnreferencedReceiver")
inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

/**
 * combineClick의 Ripple 효과를 제거하기 위한 함수입니다.
 */
@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("ModifierFactoryUnreferencedReceiver")
inline fun Modifier.noRippleCombineClickable(
    crossinline onClick: () -> Unit = {},
    crossinline onLongClick: () -> Unit,
): Modifier = composed {
    combinedClickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = { onClick() },
        onLongClick = { onLongClick() },
    )
}

/**
 * 클릭 시 Scale(95%) + Dim(10%) 효과를 주는 클릭 함수입니다.
 * 터치 시 크기가 줄어들고 약간 어두워지며, 놓으면 원래대로 돌아옵니다.
 */
@SuppressLint("ModifierFactoryUnreferencedReceiver")
inline fun Modifier.scaleDimClickable(
    shape: Shape = RectangleShape,
    crossinline onClick: () -> Unit
): Modifier = composed {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.95f else 1f,
        animationSpec = tween(100),
        label = "scale"
    )
    val dim by animateFloatAsState(
        targetValue = if (pressed) 0.10f else 0f,
        animationSpec = tween(100),
        label = "dim"
    )

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
            clip = true
            this.shape = shape
        }
        .drawWithContent {
            drawContent()
            if (dim > 0f) drawRect(Color.Black.copy(alpha = dim))
        }
        .clickable(
            interactionSource = interactionSource,
            indication = null
        ) { onClick() }
}