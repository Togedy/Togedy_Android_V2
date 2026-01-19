package com.together.study.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

private val LocalTogedyColors = staticCompositionLocalOf<TogedyColors> {
    error("No TogedyColors provided")
}

private val LocalTogedyTypography = staticCompositionLocalOf<TogedyTypography> {
    error("No TogedyColors provided")
}

object TogedyTheme {
    val colors: TogedyColors
        @Composable
        @ReadOnlyComposable
        get() = LocalTogedyColors.current
    val typography: TogedyTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTogedyTypography.current
}

@Composable
fun ProvideTogedyColorsAndTypography(
    colors: TogedyColors,
    typography: TogedyTypography,
    content: @Composable () -> Unit
) {
    val provideColors = remember { colors.copy() }.apply { update(colors) }
    CompositionLocalProvider(
        LocalTogedyColors provides provideColors,
        LocalTogedyTypography provides typography,
        content = content
    )
}

@Composable
fun TogedyTheme(
    content: @Composable () -> Unit
) {
    val colors = TogedyLightColors()
    val typography = TogedyTypography()

    ProvideTogedyColorsAndTypography(
        colors = colors,
        typography = typography
    ) {
        MaterialTheme(
            content = content
        )
    }
}
