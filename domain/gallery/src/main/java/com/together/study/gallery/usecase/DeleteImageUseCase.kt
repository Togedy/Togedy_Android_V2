package com.together.study.gallery.usecase

import com.together.study.gallery.repository.GalleryRepository
import javax.inject.Inject

class DeleteImageUseCase @Inject constructor(
    private val repository: GalleryRepository,
) {

    suspend operator fun invoke(date: String): Result<Unit> {
        return repository.deleteImage(date)
    }
}
