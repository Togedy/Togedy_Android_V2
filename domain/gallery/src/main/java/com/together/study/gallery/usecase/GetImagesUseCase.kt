package com.together.study.gallery.usecase

import com.together.study.gallery.repository.GalleryRepository
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(
    private val repository: GalleryRepository,
) {
    suspend operator fun invoke(bucketId: Long?) =
        repository.getImages(bucketId)
}
