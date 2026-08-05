package com.together.study.designsystem.theme

import android.app.Activity
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * 각 화면 시스템바 아이콘 명암 지정
 *
 * @param darkStatusBarIcons 상태바 아이콘을 어둡게 그릴지 여부
 * @param darkNavigationBarIcons 내비게이션바 아이콘을 어둡게 그릴지 여부
 */
@Composable
fun SystemBarIcons(
    darkStatusBarIcons: Boolean = true,
    darkNavigationBarIcons: Boolean = darkStatusBarIcons,
) {
    val view = LocalView.current
    if (view.isInEditMode) return

    val window = remember(view) {
        generateSequence(view.context) { (it as? ContextWrapper)?.baseContext }
            .filterIsInstance<Activity>()
            .firstOrNull()
            ?.window
    } ?: return

    SideEffect {
        val controller = WindowCompat.getInsetsController(window, view)
        controller.isAppearanceLightStatusBars = darkStatusBarIcons
        controller.isAppearanceLightNavigationBars = darkNavigationBarIcons
    }
}
