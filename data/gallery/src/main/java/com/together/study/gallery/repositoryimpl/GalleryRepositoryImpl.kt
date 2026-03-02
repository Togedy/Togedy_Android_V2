package com.together.study.gallery.repositoryimpl

import android.content.Context
import android.provider.MediaStore.Images.Media
import com.together.study.gallery.model.GalleryAlbum
import com.together.study.gallery.model.GalleryImage
import com.together.study.gallery.repository.GalleryRepository
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(
    private val context: Context,
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
}