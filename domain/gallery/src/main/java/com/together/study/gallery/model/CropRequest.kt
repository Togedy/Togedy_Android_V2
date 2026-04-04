package com.together.study.gallery.model

data class CropRequest(
    val imageId: Long,
    val scale: Float,
    val offsetX: Float,
    val offsetY: Float,
    val cropWidth: Float,
    val cropHeight: Float,
    val originalWidth: Float,
    val originalHeight: Float,
    val viewWidth: Float,
    val viewHeight: Float,
)
