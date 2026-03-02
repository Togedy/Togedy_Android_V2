package com.together.study.gallery.usecase

import com.together.study.gallery.repository.GalleryRepository

class GetImagesUseCase(
    private val repository: GalleryRepository,
) {
    suspend operator fun invoke(bucketId: Long?) =
        repository.getImages(bucketId)
}
