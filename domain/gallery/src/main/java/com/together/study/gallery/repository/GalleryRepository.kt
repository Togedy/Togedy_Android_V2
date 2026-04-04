package com.together.study.gallery.repository

import com.together.study.gallery.model.CropRequest
import com.together.study.gallery.model.GalleryAlbum
import com.together.study.gallery.model.GalleryImage

interface GalleryRepository {
    suspend fun getAlbums(): List<GalleryAlbum>
    suspend fun getImages(buketId: Long?): List<GalleryImage>
    suspend fun cropImage(
        request: CropRequest,
    ): Result<String>

    suspend fun uploadImage(
        filePath: String,
        date: String,
    ): Result<Unit>

    suspend fun deleteImage(date: String): Result<Unit>
}
