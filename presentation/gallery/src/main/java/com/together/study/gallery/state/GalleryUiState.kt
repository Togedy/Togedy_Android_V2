package com.together.study.gallery.state

import com.together.study.gallery.GalleryAlbum
import com.together.study.gallery.GalleryImage
import com.together.study.gallery.GalleryMonthSection

data class GalleryUiState(
    val albums: List<GalleryAlbum> = emptyList(),
    val selectedAlbum: GalleryAlbum? = null,
    val images: List<GalleryImage> = emptyList(),
    val monthSections: List<GalleryMonthSection> = emptyList(),
    val isAlbumSheetOpen: Boolean = false,
)
