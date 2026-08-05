package com.together.study.gallery.util

import android.Manifest
import android.content.Context
import android.os.Build
import com.together.study.util.isPermissionGranted

internal enum class MediaAccessLevel {
    FULL, // 전체 허용

    PARTIAL, // 제한적 접근 허용

    DENIED, // 허용 안함
}

internal val mediaPermissions: Array<String> = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE -> arrayOf(
        Manifest.permission.READ_MEDIA_IMAGES,
        Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED,
    )

    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> arrayOf(
        Manifest.permission.READ_MEDIA_IMAGES,
    )

    else -> arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
}

internal fun Context.mediaAccessLevel(): MediaAccessLevel {
    val fullAccessPermission =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) Manifest.permission.READ_MEDIA_IMAGES
        else Manifest.permission.READ_EXTERNAL_STORAGE

    return when {
        isPermissionGranted(fullAccessPermission) -> MediaAccessLevel.FULL

        Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE &&
            isPermissionGranted(Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED) ->
            MediaAccessLevel.PARTIAL

        else -> MediaAccessLevel.DENIED
    }
}
