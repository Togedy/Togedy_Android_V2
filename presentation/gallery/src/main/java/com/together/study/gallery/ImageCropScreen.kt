package com.together.study.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.together.study.designsystem.R
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.gallery.util.toUri
import com.together.study.util.noRippleClickable

@Composable
internal fun ImageCropScreen(
    imageId: Long,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onDoneClick: () -> Unit,
) {
    val context = LocalContext.current
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val uri = remember(imageId) { imageId.toUri() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.black),
    ) {
        ImageCropTopBar(
            onBackClick = onBackClick,
            onDoneClick = onDoneClick,
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(300.dp) // 고정 크롭 박스
                    .clipToBounds()
            ) {
                AsyncImage(
                    model = uri,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            translationX = offset.x
                            translationY = offset.y
                        }
                        .pointerInput(Unit) {
                            detectTransformGestures { _, pan, zoom, _ ->
                                scale *= zoom
                                offset += pan
                            }
                        }
                )
            }

            // 반투명 오버레이
            Box(
                Modifier
                    .matchParentSize()
                    .background(TogedyTheme.colors.black.copy(alpha = 0.5f)),
            )

            // 크롭 영역 테두리
            Box(
                Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .aspectRatio(328f / 114f)
                    .border(1.dp, TogedyTheme.colors.white, RoundedCornerShape(16.dp)),
            )
        }

        ImageCropBottomMenu(
            onResetClick = { },
            onFitClick = { },
        )
    }
}

@Composable
private fun ImageCropTopBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onDoneClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 16.dp),
    ) {
        Text(
            text = "취소",
            style = TogedyTheme.typography.title16sb,
            color = TogedyTheme.colors.gray500,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .noRippleClickable(onBackClick),
        )

        Text(
            text = "이미지 크롭",
            style = TogedyTheme.typography.title16sb,
            color = TogedyTheme.colors.white,
            modifier = Modifier.align(Alignment.Center)
        )

        Text(
            text = "완료",
            style = TogedyTheme.typography.title16sb,
            color = TogedyTheme.colors.green,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .noRippleClickable(onDoneClick),
        )
    }
}

@Composable
private fun ImageCropBottomMenu(
    modifier: Modifier = Modifier,
    onResetClick: () -> Unit,
    onFitClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .noRippleClickable(onResetClick),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_reset),
                contentDescription = null,
                tint = TogedyTheme.colors.gray500,
            )
            Spacer(Modifier.width(14.dp))
            Text(
                text = "초기화",
                style = TogedyTheme.typography.title16sb,
                color = TogedyTheme.colors.gray500,
            )
        }

        Row(
            modifier = Modifier
                .weight(1f)
                .noRippleClickable(onResetClick),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_fit),
                contentDescription = null,
                tint = TogedyTheme.colors.white,
            )
            Spacer(Modifier.width(14.dp))
            Text(
                text = "화면 맞춤",
                style = TogedyTheme.typography.title16sb,
                color = TogedyTheme.colors.gray500,
            )
        }
    }
}

@Preview
@Composable
private fun ImageCropScreenPreview() {
    TogedyTheme {
        ImageCropScreen(
            imageId = 1,
            onBackClick = { },
            onDoneClick = { },
        )
    }
}