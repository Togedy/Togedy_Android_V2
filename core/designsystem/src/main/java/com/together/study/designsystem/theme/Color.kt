package com.together.study.designsystem.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

val GREEN = Color(0xFF11BC78)
val GREEN_BG = Color(0xFFEEFAF6)
val RED = Color(0xFFD13E3E)
val RED30 = Color(0x4DFF6363)
val BLUE = Color(0xFF677BFF)

val WHITE = Color(0xFFFFFFFF)
val GRAY50 = Color(0xFFF8F8FA)
val GRAY100 = Color(0xFFF2F3F5)
val GRAY200 = Color(0xFFEBEDF0) //button-bg
val GRAY300 = Color(0xFFD5D9DF)
val GRAY400 = Color(0xFFC4C8CC)
val GRAY500 = Color(0xFFA8AEB4) //gray/picker_text
val GRAY600 = Color(0xFF5F6773) //gray/button_text
val GRAY700 = Color(0xFF49505C) //gray/TEXT
val GRAY800 = Color(0xFF393E47)
val GRAY900 = Color(0xFF4B525E)
val BLACK = Color(0xFF000000)

@Stable
class TogedyColors(
    green: Color,
    greenBg: Color,
    red: Color,
    red30: Color,
    blue: Color,
    white: Color,
    gray50: Color,
    gray100: Color,
    gray200: Color,
    gray300: Color,
    gray400: Color,
    gray500: Color,
    gray600: Color,
    gray700: Color,
    gray800: Color,
    gray900: Color,
    black: Color,
) {
    var green by mutableStateOf(green)
        private set
    var greenBg by mutableStateOf(greenBg)
        private set
    var red by mutableStateOf(red)
        private set
    var red30 by mutableStateOf(red30)
        private set
    var blue by mutableStateOf(blue)
        private set
    var white by mutableStateOf(white)
        private set

    /**
     * @param gray50  BG
     */
    var gray50 by mutableStateOf(gray50)
        private set

    /**
     * @param gray100  Card_BG
     */
    var gray100 by mutableStateOf(gray100)
        private set

    /**
     * @param gray200  Btn_BG
     */
    var gray200 by mutableStateOf(gray200)
        private set

    /**
     * @param gray300  Icon_Disabled
     */
    var gray300 by mutableStateOf(gray300)
        private set

    /**
     * @param gray400  Text_Disabled
     */
    var gray400 by mutableStateOf(gray400)
        private set

    /**
     * @param gray500  Text_Sub
     */
    var gray500 by mutableStateOf(gray500)
        private set

    /**
     * @param gray600  Text_Chip
     */
    var gray600 by mutableStateOf(gray600)
        private set

    /**
     * @param gray700  Text_Body
     */
    var gray700 by mutableStateOf(gray700)
        private set

    /**
     * @param gray800  Text_Default / Icon_Default
     */
    var gray800 by mutableStateOf(gray800)
        private set

    /**
     * @param gray900  Text_Title
     */
    var gray900 by mutableStateOf(gray900)
        private set
    var black by mutableStateOf(black)
        private set

    fun copy(): TogedyColors = TogedyColors(
        green,
        greenBg,
        red,
        red30,
        blue,
        white,
        gray50,
        gray100,
        gray200,
        gray300,
        gray400,
        gray500,
        gray600,
        gray700,
        gray800,
        gray900,
        black,
    )

    fun update(other: TogedyColors) {
        green = other.green
        greenBg = other.greenBg
        red = other.red
        red30 = other.red30
        blue = other.blue
        white = other.white
        gray50 = other.gray50
        gray100 = other.gray100
        gray200 = other.gray200
        gray300 = other.gray300
        gray400 = other.gray400
        gray500 = other.gray500
        gray600 = other.gray600
        gray700 = other.gray700
        gray800 = other.gray800
        gray900 = other.gray900
        black = other.black
    }
}

fun TogedyLightColors(
    green: Color = GREEN,
    greenBg: Color = GREEN_BG,
    red: Color = RED,
    red30: Color = RED30,
    blue: Color = BLUE,
    white: Color = WHITE,
    gray50: Color = GRAY50,
    gray100: Color = GRAY100,
    gray200: Color = GRAY200,
    gray300: Color = GRAY300,
    gray400: Color = GRAY400,
    gray500: Color = GRAY500,
    gray600: Color = GRAY600,
    gray700: Color = GRAY700,
    gray800: Color = GRAY800,
    gray900: Color = GRAY900,
    black: Color = BLACK,
) = TogedyColors(
    green,
    greenBg,
    red,
    red30,
    blue,
    white,
    gray50,
    gray100,
    gray200,
    gray300,
    gray400,
    gray500,
    gray600,
    gray700,
    gray800,
    gray900,
    black,
)
