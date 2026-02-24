package com.together.study.gallery

import android.net.Uri

data class GalleryAlbum(
    val bucketId: Long,
    val name: String,
    val coverUri: Uri,
    val count: Int,
)
