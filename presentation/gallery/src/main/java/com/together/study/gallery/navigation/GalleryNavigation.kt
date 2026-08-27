package com.together.study.gallery.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.together.study.common.navigation.Route
import com.together.study.gallery.ImageCropScreen
import com.together.study.gallery.ImageCropViewModel
import com.together.study.gallery.type.CropShapeType
import kotlinx.serialization.Serializable

fun NavController.navigateToGallery(
    date: String,
    navOptions: NavOptions? = null,
) = navigate(TogedyCropImage(date), navOptions)

fun NavGraphBuilder.galleryGraph(
    modifier: Modifier = Modifier,
    navigateToUp: () -> Unit,
    onUploadSuccess: () -> Unit,
    onProfileCropSuccess: ((String) -> Unit)? = null,
) {
    composable<TogedyCropImage> { backStackEntry ->
        val route = backStackEntry.toRoute<TogedyCropImage>()
        val isProfile = route.date == ImageCropViewModel.PROFILE_DATE
        val cropShape = if (isProfile) {
            CropShapeType.Circle
        } else {
            CropShapeType.Rect(aspectRatio = 1f)
        }

        ImageCropScreen(
            cropShape = cropShape,
            onBackClick = navigateToUp,
            onUploadSuccess = onUploadSuccess,
            onCropSuccess = if (isProfile) onProfileCropSuccess else null,
            modifier = modifier,
        )
    }
}

@Serializable
data class TogedyCropImage(
    val date: String,
) : Route
