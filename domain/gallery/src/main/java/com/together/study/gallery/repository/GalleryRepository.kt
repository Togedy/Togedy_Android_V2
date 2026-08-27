package com.together.study.gallery.repository

import com.together.study.gallery.model.CropRequest

interface GalleryRepository {
    suspend fun cropImage(
        request: CropRequest,
    ): Result<String>

    suspend fun uploadImage(
        filePath: String,
        date: String,
    ): Result<Unit>

    suspend fun deleteImage(date: String): Result<Unit>
}
