package com.together.study.util.image

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.provider.MediaStore
import android.view.View
import androidx.core.graphics.createBitmap

fun captureComposable(
    view: View,
    bounds: Rect,
): Bitmap {
    val bitmap = createBitmap(bounds.width(), bounds.height())

    val canvas = Canvas(bitmap)
    canvas.translate(-bounds.left.toFloat(), -bounds.top.toFloat())
    view.draw(canvas)

    return bitmap
}

fun saveBitmapToGallery(
    selectedDate: String,
    context: Context,
    bitmap: Bitmap,
) {
    val fileName = "capture_${selectedDate}_${System.currentTimeMillis()}.png"

    val values = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
        put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MyApp")
    }

    val uri = context.contentResolver.insert(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        values
    )

    uri?.let {
        context.contentResolver.openOutputStream(it)?.use { stream ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        }
    }
}
