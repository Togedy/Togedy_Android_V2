package com.together.study.gallery.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.together.study.common.navigation.Route
import com.together.study.gallery.GalleryScreen
import kotlinx.serialization.Serializable

fun NavController.navigateToGallery(
    navOptions: NavOptions? = null,
) = navigate(TogedyGallery, navOptions)

fun NavGraphBuilder.galleryGraph(
    modifier: Modifier = Modifier,
    navigateToUp: () -> Unit,
    navController: NavController,
) {
    composable<TogedyGallery> {
        GalleryScreen(
            onBackClick = navigateToUp,
            onImageClick = { },
            modifier = modifier,
        )
    }
}

@Serializable
data object TogedyGallery : Route