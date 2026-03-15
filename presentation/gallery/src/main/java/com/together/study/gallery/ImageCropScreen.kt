package com.together.study.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.together.study.designsystem.R
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.gallery.model.CropRequest
import com.together.study.gallery.type.CropShapeType
import com.together.study.gallery.util.toUri
import com.together.study.util.noRippleClickable

private const val ResetHeightRatio = 0.8f

private data class CropTransform(
    val scale: Float,
    val offset: Offset,
)

/**
 * 이미지 크롭 화면
 *
 * 동작 기준
 * - `Fit`은 현재 크롭 영역을 빈 공간 없이 정확히 채우는 최소 배율입니다.
 * - `초기화`는 이미지 높이가 화면 높이의 80%를 차지하도록 맞추되, 크롭 영역을 못 채우면 `Fit` 배율로 보정합니다.
 * - 첫 진입 시 초기 배율과 위치도 `초기화`와 동일하게 적용합니다.
 *
 * 표시 차이는 원본 해상도보다 이미지 비율의 영향을 더 크게 받습니다.
 * - `4080x3060` 같은 4:3 가로 사진은 `초기화`가 `Fit`보다 훨씬 크게 보입니다. (세로로 찍은 사진)
 * - `1080x2340` 같은 세로로 긴 사진은 `초기화`와 `Fit`이 거의 같게 보입니다. (스크린샷 등)
 * - `3023x4032` 같은 3:4 세로 사진은 `초기화`가 `Fit`보다 더 크게 보입니다. (가로로 찍은 사진)
 */
@Composable
internal fun ImageCropScreen(
    imageId: Long,
    cropShape: CropShapeType,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewModel: ImageCropViewModel = hiltViewModel(),
) {
    var isInitTransformed by remember(imageId) { mutableStateOf(false) }
    var scale by remember(imageId) { mutableFloatStateOf(1f) }
    var offset by remember(imageId) { mutableStateOf(Offset.Zero) }
    val uri = remember(imageId) { imageId.toUri() }

    var imageWidth by remember(imageId) { mutableFloatStateOf(0f) }
    var imageHeight by remember(imageId) { mutableFloatStateOf(0f) }

    var viewWidth by remember(imageId) { mutableFloatStateOf(0f) }
    var viewHeight by remember(imageId) { mutableFloatStateOf(0f) }

    var cropWidth by remember(imageId) { mutableFloatStateOf(0f) }
    var cropHeight by remember(imageId) { mutableFloatStateOf(0f) }

    val fitScale = calculateFitScale(
        imageWidth = imageWidth,
        imageHeight = imageHeight,
        viewWidth = viewWidth,
        viewHeight = viewHeight,
    )
    val minScale = calculateMinCropScale(
        fitScale = fitScale,
        imageWidth = imageWidth,
        imageHeight = imageHeight,
        cropWidth = cropWidth,
        cropHeight = cropHeight,
    )
    val resetScale = calculateResetScale(
        imageHeight = imageHeight,
        viewHeight = viewHeight,
        fitScale = fitScale,
        minScale = minScale,
    )

    val isInitTransformCalculated =
        fitScale > 0f && cropWidth > 0f && cropHeight > 0f && resetScale > 0f

    val applyCenteredTransform: (Float) -> Unit = { targetScale ->
        scale = targetScale
        offset = Offset.Zero
    }

    val transformableState = rememberTransformableState { zoomChange, panChange, _ ->
        val nextTransform = calculateGestureTransform(
            currentScale = scale,
            currentOffset = offset,
            zoomChange = zoomChange,
            panChange = panChange,
            minScale = minScale,
            fitScale = fitScale,
            imageWidth = imageWidth,
            imageHeight = imageHeight,
            cropWidth = cropWidth,
            cropHeight = cropHeight,
        )
        scale = nextTransform.scale
        offset = nextTransform.offset
    }

    LaunchedEffect(isInitTransformCalculated, resetScale, imageId) {
        if (!isInitTransformed && isInitTransformCalculated) {
            applyCenteredTransform(resetScale)
            isInitTransformed = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.black),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            AsyncImage(
                model = uri,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .onGloballyPositioned { coordinates ->
                        viewWidth = coordinates.size.width.toFloat()
                        viewHeight = coordinates.size.height.toFloat()
                    }
                    .graphicsLayer {
                        alpha = if (isInitTransformed) 1f else 0f
                        scaleX = scale
                        scaleY = scale
                        translationX = offset.x
                        translationY = offset.y
                    }
                    .transformable(
                        state = transformableState,
                        enabled = isInitTransformed,
                    ),
                onSuccess = { state ->
                    val drawable = state.result.drawable
                    imageWidth = drawable.intrinsicWidth.toFloat()
                    imageHeight = drawable.intrinsicHeight.toFloat()
                }
            )
        }

        Box(
            Modifier
                .matchParentSize()
                .graphicsLayer { alpha = 0.99f }
                .drawWithContent {
                    val cropLeft = (size.width - cropWidth) / 2f
                    val cropTop = (size.height - cropHeight) / 2f

                    drawContent()
                    drawRect(color = Color.Black.copy(alpha = 0.5f))
                    cropShape.drawMask(
                        drawScope = this,
                        cropLeft = cropLeft,
                        cropTop = cropTop,
                        cropWidth = cropWidth,
                        cropHeight = cropHeight,
                    )
                }
        )

        Box(
            Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .aspectRatio(
                    when (cropShape) {
                        is CropShapeType.Rect -> 328f / 114f
                        is CropShapeType.Circle -> 1f
                    }
                )
                .border(
                    width = 1.dp,
                    color = TogedyTheme.colors.white,
                    shape = cropShape.borderShape,
                )
                .clip(cropShape.borderShape)
                .onGloballyPositioned { coordinates ->
                    cropWidth = coordinates.size.width.toFloat()
                    cropHeight = coordinates.size.height.toFloat()
                }
        )

        if (!isInitTransformed) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(TogedyTheme.colors.black),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(color = TogedyTheme.colors.green)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        ImageCropTopBar(
            onBackClick = onBackClick,
            onDoneClick = {
                val cropRequest = CropRequest(
                    imageId = imageId,
                    scale = scale,
                    offsetX = offset.x,
                    offsetY = offset.y,
                    cropWidth = cropWidth,
                    cropHeight = cropHeight,
                    originalWidth = imageWidth,
                    originalHeight = imageHeight,
                    viewWidth = viewWidth,
                    viewHeight = viewHeight,
                )
                viewModel.cropAndUpload(cropRequest)
            },
        )

        Spacer(Modifier.weight(1f))

        ImageCropBottomMenu(
            onResetClick = {
                applyCenteredTransform(resetScale)
            },
            onFitClick = {
                if (fitScale > 0f) {
                    applyCenteredTransform(minScale)
                }
            },
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
            .navigationBarsPadding()
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
                .noRippleClickable(onFitClick),
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
                text = "Fit",
                style = TogedyTheme.typography.title16sb,
                color = TogedyTheme.colors.gray500,
            )
        }
    }
}

@Preview
@Composable
private fun RectImageCropScreenPreview() {
    TogedyTheme {
        ImageCropScreen(
            imageId = 1,
            cropShape = CropShapeType.Rect(aspectRatio = 1f),
            onBackClick = { },
        )
    }
}

@Preview
@Composable
private fun CircleImageCropScreenPreview() {
    TogedyTheme {
        ImageCropScreen(
            imageId = 1,
            cropShape = CropShapeType.Circle,
            onBackClick = { },
        )
    }
}

private fun calculateFitScale(
    imageWidth: Float,
    imageHeight: Float,
    viewWidth: Float,
    viewHeight: Float,
): Float {
    if (imageWidth <= 0f || imageHeight <= 0f || viewWidth <= 0f || viewHeight <= 0f) {
        return 0f
    }

    return minOf(
        viewWidth / imageWidth,
        viewHeight / imageHeight,
    )
}

private fun calculateMinCropScale(
    fitScale: Float,
    imageWidth: Float,
    imageHeight: Float,
    cropWidth: Float,
    cropHeight: Float,
): Float {
    if (
        fitScale <= 0f ||
        imageWidth <= 0f ||
        imageHeight <= 0f ||
        cropWidth <= 0f ||
        cropHeight <= 0f
    ) {
        return 1f
    }

    return maxOf(
        cropWidth / (imageWidth * fitScale),
        cropHeight / (imageHeight * fitScale),
    )
}

private fun calculateResetScale(
    imageHeight: Float,
    viewHeight: Float,
    fitScale: Float,
    minScale: Float,
): Float {
    if (imageHeight <= 0f || viewHeight <= 0f || fitScale <= 0f) {
        return 1f
    }

    return maxOf(
        (viewHeight * ResetHeightRatio) / (imageHeight * fitScale),
        minScale,
    )
}

private fun calculateGestureTransform(
    currentScale: Float,
    currentOffset: Offset,
    zoomChange: Float,
    panChange: Offset,
    minScale: Float,
    fitScale: Float,
    imageWidth: Float,
    imageHeight: Float,
    cropWidth: Float,
    cropHeight: Float,
): CropTransform {
    if (fitScale <= 0f) {
        return CropTransform(
            scale = currentScale,
            offset = currentOffset,
        )
    }

    val nextScale = (currentScale * zoomChange).coerceAtLeast(minScale)
    val nextOffset = calculateBoundedOffset(
        scale = nextScale,
        rawOffset = currentOffset + panChange,
        imageWidth = imageWidth,
        imageHeight = imageHeight,
        fitScale = fitScale,
        cropWidth = cropWidth,
        cropHeight = cropHeight,
    )

    return CropTransform(
        scale = nextScale,
        offset = nextOffset,
    )
}

private fun calculateBoundedOffset(
    scale: Float,
    rawOffset: Offset,
    imageWidth: Float,
    imageHeight: Float,
    fitScale: Float,
    cropWidth: Float,
    cropHeight: Float,
): Offset {
    val displayedWidth = imageWidth * fitScale
    val displayedHeight = imageHeight * fitScale
    val maxX = ((displayedWidth * scale - cropWidth) / 2f).coerceAtLeast(0f)
    val maxY = ((displayedHeight * scale - cropHeight) / 2f).coerceAtLeast(0f)

    return Offset(
        x = rawOffset.x.coerceIn(-maxX, maxX),
        y = rawOffset.y.coerceIn(-maxY, maxY),
    )
}