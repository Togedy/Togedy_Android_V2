package com.together.study.gallery.repository

import com.together.study.gallery.model.GalleryAlbum
import com.together.study.gallery.model.GalleryImage

interface GalleryRepository {
    suspend fun getAlbums(): List<GalleryAlbum>
    suspend fun getImages(buketId: Long?): List<GalleryImage>
}
