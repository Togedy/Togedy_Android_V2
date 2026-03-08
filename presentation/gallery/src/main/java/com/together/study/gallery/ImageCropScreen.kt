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
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
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

@Composable
internal fun ImageCropScreen(
    imageId: Long,
    cropShape: CropShapeType,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewModel: ImageCropViewModel = hiltViewModel(),
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val uri = remember(imageId) { imageId.toUri() }

    var imageWidth by remember { mutableFloatStateOf(0f) }
    var imageHeight by remember { mutableFloatStateOf(0f) }

    var cropWidth by remember { mutableFloatStateOf(0f) }
    var cropHeight by remember { mutableFloatStateOf(0f) }

    var minScale by remember { mutableFloatStateOf(1f) }

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
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        translationX = offset.x
                        translationY = offset.y
                    }
                    .pointerInput(imageWidth, imageHeight, cropWidth, cropHeight, minScale) {
                        detectTransformGestures { _, pan, zoom, _ ->
                            val newScale = (scale * zoom).coerceAtLeast(minScale)

                            val scaledWidth = imageWidth * newScale
                            val scaledHeight = imageHeight * newScale

                            val maxX = ((scaledWidth - cropWidth) / 2f).coerceAtLeast(0f)
                            val maxY = ((scaledHeight - cropHeight) / 2f).coerceAtLeast(0f)

                            val newOffset = offset + pan

                            offset = Offset(
                                newOffset.x.coerceIn(-maxX, maxX),
                                newOffset.y.coerceIn(-maxY, maxY)
                            )

                            scale = newScale
                        }
                    },
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

                    if (imageWidth > 0 && imageHeight > 0) {
                        minScale = cropShape.calculateMinScale(
                            imageWidth,
                            imageHeight,
                            cropWidth,
                            cropHeight
                        )
                        scale = minScale
                        offset = Offset.Zero
                    }
                }
        )
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
                )
                viewModel.cropAndUpload(cropRequest)
            },
        )

        Spacer(Modifier.weight(1f))

        ImageCropBottomMenu(
            onResetClick = {
                scale = 1f
                offset = Offset.Zero
            },
            onFitClick = {
                if (imageWidth > 0f) {
                    val cropFitScale = cropWidth / imageWidth
                    scale = cropFitScale
                    offset = Offset.Zero
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