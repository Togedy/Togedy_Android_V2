package com.together.study.gallery.util

import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore

fun Long.toUri(): Uri =
    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, this)
