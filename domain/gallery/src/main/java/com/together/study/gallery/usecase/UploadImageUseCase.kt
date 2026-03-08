package com.together.study.gallery.usecase

import com.together.study.gallery.repository.GalleryRepository
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
    private val repository: GalleryRepository,
) {

    suspend operator fun invoke(
        filePath: String,
    ): Result<Unit> {
        return repository.uploadImage(filePath)
    }
}
