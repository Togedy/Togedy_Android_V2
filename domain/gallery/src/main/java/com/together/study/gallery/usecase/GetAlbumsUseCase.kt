package com.together.study.gallery.usecase

import com.together.study.gallery.repository.GalleryRepository
import javax.inject.Inject

class GetAlbumsUseCase @Inject constructor(
    private val repository: GalleryRepository,
) {
    suspend operator fun invoke() = repository.getAlbums()
}
