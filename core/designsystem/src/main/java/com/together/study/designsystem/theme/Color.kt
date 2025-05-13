package com.together.study.designsystem.theme

import androidx.compose.runtime.*
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

val GREEN = Color(0xFF11BC78)
val GREEN_BG = Color(0xFFEEFAF6)

val WHITE = Color(0xFFFFFFFF)
val GRAY100 = Color(0xFFF8F8FA)
val GRAY200 = Color(0xFFF2F3F5)
val GRAY300 = Color(0xFFD5D9DF)
val GRAY400 = Color(0xFFB4B8BF)
val GRAY500 = Color(0xFF8E9197)
val GRAY600 = Color(0xFF5D626B)
val GRAY700 = Color(0xFF4B525E)
val BLACK = Color(0x00000000)

@Stable
class TogedyColors(
    green: Color,
    greenBg: Color,
    white: Color,
    gray100: Color,
    gray200: Color,
    gray300: Color,
    gray400: Color,
    gray500: Color,
    gray600: Color,
    gray700: Color,
    black: Color,
) {
    var green by mutableStateOf(green)
        private set
    var greenBg by mutableStateOf(greenBg)
        private set
    var white by mutableStateOf(white)
        private set
    var gray100 by mutableStateOf(gray100)
        private set
    var gray200 by mutableStateOf(gray200)
        private set
    var gray300 by mutableStateOf(gray300)
        private set
    var gray400 by mutableStateOf(gray400)
        private set
    var gray500 by mutableStateOf(gray500)
        private set
    var gray600 by mutableStateOf(gray600)
        private set
    var gray700 by mutableStateOf(gray700)
        private set
    var black by mutableStateOf(black)
        private set

    fun copy(): TogedyColors = TogedyColors(
        green,
        greenBg,
        white,
        gray100,
        gray200,
        gray300,
        gray400,
        gray500,
        gray600,
        gray700,
        black,
    )

    fun update(other: TogedyColors) {
        green = other.green
        greenBg = other.greenBg
        white = other.white
        gray100 = other.gray100
        gray200 = other.gray200
        gray300 = other.gray300
        gray400 = other.gray400
        gray500 = other.gray500
        gray600 = other.gray600
        gray700 = other.gray700
        black = other.black
    }
}

fun TogedyLightColors(
    green: Color = GREEN,
    greenBg: Color = GREEN_BG,
    white: Color = WHITE,
    gray100: Color = GRAY100,
    gray200: Color = GRAY200,
    gray300: Color = GRAY300,
    gray400: Color = GRAY400,
    gray500: Color = GRAY500,
    gray600: Color = GRAY600,
    gray700: Color = GRAY700,
    black: Color = BLACK,
) = TogedyColors(
    green,
    greenBg,
    white,
    gray100,
    gray200,
    gray300,
    gray400,
    gray500,
    gray600,
    gray700,
    black,
)
