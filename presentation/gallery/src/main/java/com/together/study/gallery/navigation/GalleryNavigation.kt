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
    navOptions: NavOptions? = null,
) = navigate(TogedyGallery, navOptions)

fun NavController.navigateToCorpImage(
    imageId: Long,
    navOptions: NavOptions? = null,
) = navigate(TogedyCorpImage(imageId), navOptions)

fun NavGraphBuilder.galleryGraph(
    modifier: Modifier = Modifier,
    navigateToUp: () -> Unit,
    navController: NavController,
) {
    composable<TogedyGallery> {
        GalleryScreen(
            onBackClick = navigateToUp,
            onImageClick = navController::navigateToCorpImage,
            modifier = modifier,
        )
    }

    composable<TogedyCorpImage> { backStackEntry ->
        val route = backStackEntry.toRoute<TogedyCorpImage>()
        ImageCropScreen(
            imageId = route.imageId,
            cropShape = CropShapeType.Rect(aspectRatio = 1f),
            onBackClick = navigateToUp,
            modifier = modifier,
        )
    }
}

@Serializable
data object TogedyGallery : Route

@Serializable
data class TogedyCorpImage(
    val imageId: Long,
) : Route