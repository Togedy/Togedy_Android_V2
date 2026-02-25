package com.together.study.gallery

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore.Images.Media
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.together.study.gallery.state.GalleryUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GalleryViewModel : ViewModel() {
    var uiState by mutableStateOf(GalleryUiState())
        private set

    fun load(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val albums = loadAlbums(context)
            val images = loadImagesByAlbum(context, null)
            val sections = images.toMonthSections()

            withContext(Dispatchers.Main) {
                uiState = uiState.copy(
                    albums = albums,
                    images = images,
                    monthSections = sections,
                )
            }
        }
    }

    fun updateAlbumSheetState() {
        uiState = uiState.copy(isAlbumSheetOpen = !uiState.isAlbumSheetOpen)
    }

    fun selectAlbum(context: Context, album: GalleryAlbum?) {
        viewModelScope.launch(Dispatchers.IO) {
            val images = loadImagesByAlbum(context, album?.bucketId)
            val sections = images.toMonthSections()

            withContext(Dispatchers.Main) {
                uiState = uiState.copy(
                    selectedAlbum = album,
                    images = images,
                    monthSections = sections,
                    isAlbumSheetOpen = false,
                )
            }
        }
    }
}

private fun loadAlbums(context: Context): List<GalleryAlbum> {
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
        val idCol = cursor.getColumnIndexOrThrow(Media._ID)
        val bucketIdCol = cursor.getColumnIndexOrThrow(Media.BUCKET_ID)
        val bucketNameCol = cursor.getColumnIndexOrThrow(Media.BUCKET_DISPLAY_NAME)

        while (cursor.moveToNext()) {
            val imageId = cursor.getLong(idCol)
            val bucketId = cursor.getLong(bucketIdCol)
            val name = cursor.getString(bucketNameCol) ?: "Unknown"

            if (albumMap.containsKey(bucketId)) {
                countMap[bucketId] = countMap[bucketId]!! + 1
            } else {
                val coverUri = ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, imageId)

                albumMap[bucketId] = GalleryAlbum(
                    bucketId = bucketId,
                    name = name,
                    coverUri = coverUri,
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

fun loadImagesByAlbum(
    context: Context,
    bucketId: Long?,
): List<GalleryImage> {
    val images = mutableListOf<GalleryImage>()
    val projection = arrayOf(Media._ID, Media.DATE_TAKEN, Media.DATE_ADDED)

    val selection =
        if (bucketId == null) null
        else "${Media.BUCKET_ID} = ?"

    val selectionArgs =
        if (bucketId == null) null
        else arrayOf(bucketId.toString())

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

            val uri = ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, id)

            images.add(GalleryImage(id, uri, dateMillis))
        }
    }

    return images
}
