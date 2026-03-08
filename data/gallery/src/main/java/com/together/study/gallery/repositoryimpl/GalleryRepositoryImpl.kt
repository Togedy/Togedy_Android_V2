package com.together.study.gallery.repositoryimpl

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Environment
import android.provider.MediaStore.Images.Media
import com.together.study.gallery.model.CropRequest
import com.together.study.gallery.model.GalleryAlbum
import com.together.study.gallery.model.GalleryImage
import com.together.study.gallery.repository.GalleryRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : GalleryRepository {
    override suspend fun getAlbums(): List<GalleryAlbum> {
        val albumMap = linkedMapOf<Long, GalleryAlbum>()
        val countMap = mutableMapOf<Long, Int>()
        val projection = arrayOf(Media._ID, Media.BUCKET_ID, Media.BUCKET_DISPLAY_NAME)

        val sortOrder = """
        ${Media.DATE_TAKEN} DESC,
        ${Media.DATE_ADDED} DESC
    """.trimIndent()

        context.contentResolver.query(
            Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            sortOrder,
        )?.use { cursor ->
            val bucketIdCol = cursor.getColumnIndexOrThrow(Media.BUCKET_ID)
            val bucketNameCol = cursor.getColumnIndexOrThrow(Media.BUCKET_DISPLAY_NAME)

            while (cursor.moveToNext()) {
                val bucketId = cursor.getLong(bucketIdCol)
                val name = cursor.getString(bucketNameCol) ?: "Unknown"

                if (albumMap.containsKey(bucketId)) {
                    countMap[bucketId] = countMap[bucketId]!! + 1
                } else {
                    albumMap[bucketId] = GalleryAlbum(
                        bucketId = bucketId,
                        name = name,
                        count = 0, // 임시
                    )
                    countMap[bucketId] = 1
                }
            }
        }

        return albumMap.map { (bucketId, album) ->
            album.copy(count = countMap[bucketId] ?: 0)
        }
    }

    override suspend fun getImages(buketId: Long?): List<GalleryImage> {
        val images = mutableListOf<GalleryImage>()
        val projection = arrayOf(Media._ID, Media.DATE_TAKEN, Media.DATE_ADDED)

        val selection =
            if (buketId == null) null
            else "${Media.BUCKET_ID} = ?"

        val selectionArgs =
            if (buketId == null) null
            else arrayOf(buketId.toString())

        val sortOrder = "${Media.DATE_ADDED} DESC"

        context.contentResolver.query(
            Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )?.use { cursor ->
            val idCol = cursor.getColumnIndexOrThrow(Media._ID)
            val takenCol = cursor.getColumnIndexOrThrow(Media.DATE_TAKEN)
            val addedCol = cursor.getColumnIndexOrThrow(Media.DATE_ADDED)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idCol)
                val taken = cursor.getLong(takenCol)
                val added = cursor.getLong(addedCol) * 1000

                val dateMillis = if (taken > 0) taken else added

                images.add(GalleryImage(id, dateMillis))
            }
        }

        return images
    }

    override suspend fun cropImage(
        request: CropRequest,
    ): Result<String> = withContext(Dispatchers.IO) {

        runCatching {
            val uri =
                ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, request.imageId)

            val source =
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, uri))

            val left = (
                    (-request.offsetX + request.cropWidth / 2f) / request.scale
                    ).coerceIn(0f, request.originalWidth)

            val top = (
                    (-request.offsetY + request.cropHeight / 2f) / request.scale
                    ).coerceIn(0f, request.originalHeight)

            val width = (
                    request.cropWidth / request.scale
                    ).coerceAtMost(request.originalWidth - left)

            val height = (
                    request.cropHeight / request.scale
                    ).coerceAtMost(request.originalHeight - top)

            val cropped = Bitmap.createBitmap(
                source,
                left.toInt(),
                top.toInt(),
                width.toInt(),
                height.toInt()
            )
            val fileName = "togedy_${System.currentTimeMillis()}.jpg"

            val contentValues = ContentValues().apply {
                put(Media.DISPLAY_NAME, fileName)
                put(Media.MIME_TYPE, "image/jpeg")
                put(Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/Togedy")
                put(Media.IS_PENDING, 1)
            }

            val resolver = context.contentResolver

            val imageUri = resolver.insert(
                Media.EXTERNAL_CONTENT_URI,
                contentValues
            ) ?: throw IllegalStateException("MediaStore insert failed")

            resolver.openOutputStream(imageUri).use { output ->
                cropped.compress(Bitmap.CompressFormat.JPEG, 90, output!!)
            }

            contentValues.clear()
            contentValues.put(Media.IS_PENDING, 0)
            resolver.update(imageUri, contentValues, null, null)

            source.recycle()
            cropped.recycle()

            imageUri.toString()
        }
    }

    override suspend fun uploadImage(
        filePath: String,
    ): Result<Unit> = withContext(Dispatchers.IO) {

        runCatching {

            val file = File(filePath)

            val requestFile =
                file.asRequestBody("image/jpeg".toMediaType())

            val body = MultipartBody.Part.createFormData(
                "image",
                file.name,
                requestFile
            )

//            api.uploadImage(body) TODO 실제 api 연결
        }
    }
}
