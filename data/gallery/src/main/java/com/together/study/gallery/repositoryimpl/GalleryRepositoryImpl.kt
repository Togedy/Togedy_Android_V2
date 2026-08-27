package com.together.study.gallery.repositoryimpl

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import com.together.study.gallery.model.CropRequest
import com.together.study.gallery.repository.GalleryRepository
import com.together.study.gallery.service.GalleryService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import kotlin.math.roundToInt
import androidx.core.graphics.scale

private const val MAX_IMAGE_SIZE = 1280

class GalleryRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val galleryService: GalleryService,
) : GalleryRepository {
    private data class CropBitmapRect(
        val left: Int,
        val top: Int,
        val width: Int,
        val height: Int,
    )

    override suspend fun cropImage(
        request: CropRequest,
    ): Result<String> = withContext(Dispatchers.IO) {

        runCatching {
            val uri = Uri.parse(request.uri)

            val source =
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, uri))

            val bitmapWidth = source.width.toFloat()
            val bitmapHeight = source.height.toFloat()

            val fitScale = minOf(
                request.viewWidth / bitmapWidth,
                request.viewHeight / bitmapHeight
            )
            val totalScale = fitScale * request.scale
            require(totalScale > 0f) { "유효하지 않는 scale 값입니다: $totalScale" }

            val cropRect = calculateCropBitmapRect(
                request = request,
                bitmapWidth = bitmapWidth,
                bitmapHeight = bitmapHeight,
                totalScale = totalScale,
            )
            val cropped = Bitmap.createBitmap(
                source,
                cropRect.left,
                cropRect.top,
                cropRect.width,
                cropRect.height,
            )

            val maxSide = MAX_IMAGE_SIZE
            val resized = if (cropped.width > maxSide || cropped.height > maxSide) {
                val ratio = maxSide.toFloat() / maxOf(cropped.width, cropped.height)
                val newWidth = (cropped.width * ratio).roundToInt()
                val newHeight = (cropped.height * ratio).roundToInt()
                cropped.scale(newWidth, newHeight).also {
                    cropped.recycle()
                }
            } else {
                cropped
            }

            val tempFile = File(context.cacheDir, "togedy_crop_${System.currentTimeMillis()}.jpg")

            FileOutputStream(tempFile).use { output ->
                resized.compress(Bitmap.CompressFormat.JPEG, 90, output)
            }

            source.recycle()
            resized.recycle()

            tempFile.absolutePath
        }
    }

    override suspend fun uploadImage(
        filePath: String,
        date: String,
    ): Result<Unit> = withContext(Dispatchers.IO) {

        runCatching {
            val file = File(filePath)

            try {
                val requestFile = file.asRequestBody("image/jpeg".toMediaType())

                val imagePart = MultipartBody.Part.createFormData(
                    "plannerImage",
                    file.name,
                    requestFile,
                )

                val removePlannerImage = "false"
                    .toRequestBody("text/plain".toMediaType())

                galleryService.uploadPlannerImage(
                    date = date,
                    plannerImage = imagePart,
                    removePlannerImage = removePlannerImage,
                )
            } finally {
                file.delete()
            }

            Unit
        }
    }

    override suspend fun deleteImage(date: String): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            val removePlannerImage = "true"
                .toRequestBody("text/plain".toMediaType())

            galleryService.uploadPlannerImage(
                date = date,
                plannerImage = null,
                removePlannerImage = removePlannerImage,
            )

            Unit
        }
    }

    private fun calculateCropBitmapRect(
        request: CropRequest,
        bitmapWidth: Float,
        bitmapHeight: Float,
        totalScale: Float,
    ): CropBitmapRect {
        val sourceWidth = bitmapWidth.roundToInt()
        val sourceHeight = bitmapHeight.roundToInt()

        val requestedWidth = (request.cropWidth / totalScale).roundToInt().coerceAtLeast(1)
        val requestedHeight = (request.cropHeight / totalScale).roundToInt().coerceAtLeast(1)

        val rawLeft = (
            bitmapWidth / 2f -
                (request.offsetX + request.cropWidth / 2f) / totalScale
            ).roundToInt()
        val rawTop = (
            bitmapHeight / 2f -
                (request.offsetY + request.cropHeight / 2f) / totalScale
            ).roundToInt()

        val left = rawLeft.coerceIn(0, (sourceWidth - 1).coerceAtLeast(0))
        val top = rawTop.coerceIn(0, (sourceHeight - 1).coerceAtLeast(0))
        val width = requestedWidth.coerceAtMost(sourceWidth - left).coerceAtLeast(1)
        val height = requestedHeight.coerceAtMost(sourceHeight - top).coerceAtLeast(1)

        return CropBitmapRect(
            left = left,
            top = top,
            width = width,
            height = height,
        )
    }
}
