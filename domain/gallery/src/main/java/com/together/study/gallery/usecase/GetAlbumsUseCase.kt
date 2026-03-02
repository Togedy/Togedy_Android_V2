package com.together.study.gallery.usecase

import com.together.study.gallery.repository.GalleryRepository

class GetAlbumsUseCase(
    private val repository: GalleryRepository,
) {
    suspend operator fun invoke() = repository.getAlbums()
}
