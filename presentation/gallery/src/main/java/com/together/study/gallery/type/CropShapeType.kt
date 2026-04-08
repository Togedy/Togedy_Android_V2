package com.together.study.gallery.type

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp

sealed class CropShapeType {
    abstract val borderShape: Shape

    abstract fun drawMask(
        drawScope: DrawScope,
        cropLeft: Float,
        cropTop: Float,
        cropWidth: Float,
        cropHeight: Float,
    )

    data class Rect(
        val aspectRatio: Float,
        val cornerRadiusDp: Float = 16f,
    ) : CropShapeType() {

        override val borderShape: Shape
            get() = RoundedCornerShape(cornerRadiusDp.dp)

        override fun drawMask(
            drawScope: DrawScope,
            cropLeft: Float,
            cropTop: Float,
            cropWidth: Float,
            cropHeight: Float,
        ) = with(drawScope) {
            drawRoundRect(
                color = Color.Transparent,
                topLeft = Offset(cropLeft, cropTop),
                size = Size(cropWidth, cropHeight),
                cornerRadius = CornerRadius(
                    cornerRadiusDp.dp.toPx(),
                    cornerRadiusDp.dp.toPx()
                ),
                blendMode = BlendMode.Clear,
            )
        }
    }

    object Circle : CropShapeType() {

        override val borderShape: Shape
            get() = CircleShape

        override fun drawMask(
            drawScope: DrawScope,
            cropLeft: Float,
            cropTop: Float,
            cropWidth: Float,
            cropHeight: Float,
        ) = with(drawScope) {
            drawCircle(
                color = Color.Transparent,
                radius = cropWidth / 2f,
                center = Offset(
                    cropLeft + cropWidth / 2f,
                    cropTop + cropHeight / 2f
                ),
                blendMode = BlendMode.Clear,
            )
        }
    }
}
