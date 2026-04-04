package com.together.study.gallery.usecase

import com.together.study.gallery.model.CropRequest
import com.together.study.gallery.repository.GalleryRepository
import javax.inject.Inject

class CropImageUseCase @Inject constructor(
    private val repository: GalleryRepository,
) {

    suspend operator fun invoke(
        request: CropRequest,
    ): Result<String> {
        return repository.cropImage(request)
    }
}
