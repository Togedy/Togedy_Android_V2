package com.together.study.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.core.graphics.scale
import java.io.File
import java.io.FileOutputStream
import kotlin.math.min

object ImageConverter {
    /**
     * Uri를 File로 변환하면서 이미지를 리사이즈 및 압축합니다.
     *
     * @param context Context
     * @param uri 변환할 이미지 Uri
     * @param maxWidth 최대 너비 (기본값: 1024)
     * @param maxHeight 최대 높이 (기본값: 1024)
     * @param quality JPEG 압축 품질 (0-100, 기본값: 85)
     * @return 압축된 이미지 File
     */
    fun uriToFile(
        context: Context,
        uri: Uri,
        maxWidth: Int = 1024,
        maxHeight: Int = 1024,
        quality: Int = 85
    ): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val file = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}.jpg")

            inputStream.use { input ->
                // 이미지를 Bitmap으로 로드
                val originalBitmap = BitmapFactory.decodeStream(input) ?: return null

                try {
                    // 이미지 리사이징 및 압축
                    val compressedBitmap = compressImage(originalBitmap, maxWidth, maxHeight)

                    // 압축된 이미지를 파일로 저장
                    FileOutputStream(file).use { output ->
                        compressedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, output)
                    }

                    // 리사이징된 bitmap이 원본과 다르면 recycle
                    if (compressedBitmap != originalBitmap) {
                        compressedBitmap.recycle()
                    }
                } finally {
                    originalBitmap.recycle()
                }
            }
            file
        } catch (e: Exception) {
            Toast.makeText(context, "이미지 변환에 실패하였습니다", Toast.LENGTH_SHORT).show()
            null
        }
    }

    /**
     * Bitmap을 지정된 최대 크기로 리사이즈합니다.
     * 비율을 유지하면서 리사이즈됩니다.
     *
     * @param bitmap 리사이즈할 Bitmap
     * @param maxWidth 최대 너비
     * @param maxHeight 최대 높이
     * @return 리사이즈된 Bitmap (원본이 최대 크기보다 작으면 원본 반환)
     */
    fun compressImage(
        bitmap: Bitmap,
        maxWidth: Int = 1024,
        maxHeight: Int = 1024
    ): Bitmap {
        val width = bitmap.width
        val height = bitmap.height

        // 이미지가 최대 크기보다 작으면 그대로 반환
        if (width <= maxWidth && height <= maxHeight) {
            return bitmap
        }

        // 비율 유지하면서 리사이징
        val scale = min(
            maxWidth.toFloat() / width,
            maxHeight.toFloat() / height
        )

        val newWidth = (width * scale).toInt()
        val newHeight = (height * scale).toInt()

        return bitmap.scale(newWidth, newHeight)
    }
}

