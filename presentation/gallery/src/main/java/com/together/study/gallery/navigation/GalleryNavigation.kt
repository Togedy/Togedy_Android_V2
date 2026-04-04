package com.together.study.gallery.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.together.study.common.navigation.Route
import com.together.study.gallery.GalleryScreen
import com.together.study.gallery.ImageCropScreen
import com.together.study.gallery.type.CropShapeType
import kotlinx.serialization.Serializable

fun NavController.navigateToGallery(
    date: String,
    navOptions: NavOptions? = null,
) = navigate(TogedyGallery(date), navOptions)

fun NavController.navigateToCorpImage(
    imageId: Long,
    date: String,
    navOptions: NavOptions? = null,
) = navigate(TogedyCorpImage(imageId, date), navOptions)

fun NavGraphBuilder.galleryGraph(
    modifier: Modifier = Modifier,
    navigateToUp: () -> Unit,
    onUploadSuccess: () -> Unit,
    navController: NavController,
) {
    composable<TogedyGallery> { backStackEntry ->
        val route = backStackEntry.toRoute<TogedyGallery>()
        GalleryScreen(
            onBackClick = navigateToUp,
            onImageClick = { imageId ->
                navController.navigateToCorpImage(imageId, route.date)
            },
            modifier = modifier,
        )
    }

    composable<TogedyCorpImage> { backStackEntry ->
        val route = backStackEntry.toRoute<TogedyCorpImage>()
        ImageCropScreen(
            imageId = route.imageId,
            cropShape = CropShapeType.Rect(aspectRatio = 1f),
            onBackClick = navigateToUp,
            onUploadSuccess = onUploadSuccess,
            modifier = modifier,
        )
    }
}

@Serializable
data class TogedyGallery(
    val date: String,
) : Route

@Serializable
data class TogedyCorpImage(
    val imageId: Long,
    val date: String,
) : Route