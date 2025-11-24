package com.together.study.designsystem.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

val GREEN = Color(0xFF11BC78)
val GREEN800 = Color(0xFF41C993)
val GREEN600 = Color(0xFF70D7AE)
val GREEN500 = Color(0xFFCFF2E4)
val GREEN400 = Color(0xFFDCF5ED)
val GREEN_BG = Color(0xFFEEFAF6)

val GOLD100 = Color(0xFFFFF5E1)
val GOLD200 = Color(0xFFFFF1D6)
val GOLD500 = Color(0xFFFFE3AB)
val GOLD900 = Color(0xFFCA8A3F)
val SILVER100 = Color(0xFFF2F2F2)
val SILVER200 = Color(0xFFEEEEEE)
val SILVER500 = Color(0xFFDEDEDE)
val SILVER900 = Color(0xFF8E8E8E)
val BRONZE100 = Color(0xFFF0EAE7)
val BRONZE200 = Color(0xFFEBE3DF)
val BRONZE500 = Color(0xFFD5C6BD)
val BRONZE900 = Color(0xFF976D3D)
val RED = Color(0xFFD13E3E)
val RED30 = Color(0x4DFF6363)
val RED50 = Color(0xFFF34822)
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
val GRAY800 = Color(0xFF393E47) //icon/default
val GRAY900 = Color(0xFF4B525E)
val BLACK = Color(0xFF000000)

@Stable
class TogedyColors(
    green: Color,
    green800: Color,
    green600: Color,
    green500: Color,
    green400: Color,
    greenBg: Color,
    gold100: Color,
    gold200: Color,
    gold500: Color,
    gold900: Color,
    sliver100: Color,
    sliver200: Color,
    sliver500: Color,
    sliver900: Color,
    bronze100: Color,
    bronze200: Color,
    bronze500: Color,
    bronze900: Color,
    red: Color,
    red30: Color,
    red50: Color,
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
    var green800 by mutableStateOf(green800)
        private set
    var green600 by mutableStateOf(green600)
        private set
    var green500 by mutableStateOf(green500)
        private set
    var green400 by mutableStateOf(green400)
        private set
    var greenBg by mutableStateOf(greenBg)
        private set
    var gold100 by mutableStateOf(gold100)
        private set
    var gold200 by mutableStateOf(gold200)
        private set
    var gold500 by mutableStateOf(gold500)
        private set
    var gold900 by mutableStateOf(gold900)
        private set
    var sliver100 by mutableStateOf(sliver100)
        private set
    var sliver200 by mutableStateOf(sliver200)
        private set
    var sliver500 by mutableStateOf(sliver500)
        private set
    var sliver900 by mutableStateOf(sliver900)
        private set
    var bronze100 by mutableStateOf(bronze100)
        private set
    var bronze200 by mutableStateOf(bronze200)
        private set
    var bronze500 by mutableStateOf(bronze500)
        private set
    var bronze900 by mutableStateOf(bronze900)
        private set
    var red by mutableStateOf(red)
        private set
    var red30 by mutableStateOf(red30)
        private set
    var red50 by mutableStateOf(red30)
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
     * @param gray200  Btn_BG, Divider Default
     */
    var gray200 by mutableStateOf(gray200)
        private set

    /**
     * @param gray300  Icon_Disabled / Border_default
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
     * @param gray600  Text_Chip / Border_abled
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
        green800,
        green600,
        green500,
        green400,
        greenBg,
        gold100,
        gold200,
        gold500,
        gold900,
        sliver100,
        sliver200,
        sliver500,
        sliver900,
        bronze100,
        bronze200,
        bronze500,
        bronze900,
        red,
        red30,
        red50,
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
        green800 = other.green800
        green600 = other.green600
        green500 = other.green500
        green400 = other.green400
        greenBg = other.greenBg
        gold100 = other.gold100
        gold200 = other.gold200
        gold500 = other.gold500
        gold900 = other.gold900
        sliver100 = other.sliver100
        sliver200 = other.sliver200
        sliver500 = other.sliver500
        sliver900 = other.sliver900
        bronze100 = other.bronze100
        bronze200 = other.bronze200
        bronze500 = other.bronze500
        bronze900 = other.bronze900
        red = other.red
        red30 = other.red30
        red50 = other.red50
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
    green800: Color = GREEN800,
    green600: Color = GREEN600,
    green500: Color = GREEN500,
    green400: Color = GREEN400,
    greenBg: Color = GREEN_BG,
    gold100: Color = GOLD100,
    gold200: Color = GOLD200,
    gold500: Color = GOLD500,
    gold900: Color = GOLD900,
    sliver100: Color = SILVER100,
    sliver200: Color = SILVER200,
    sliver500: Color = SILVER500,
    sliver900: Color = SILVER900,
    bronze100: Color = BRONZE100,
    bronze200: Color = BRONZE200,
    bronze500: Color = BRONZE500,
    bronze900: Color = BRONZE900,
    red: Color = RED,
    red30: Color = RED30,
    red50: Color = RED50,
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
    green800,
    green600,
    green500,
    green400,
    greenBg,
    gold100,
    gold200,
    gold500,
    gold900,
    sliver100,
    sliver200,
    sliver500,
    sliver900,
    bronze100,
    bronze200,
    bronze500,
    bronze900,
    red,
    red30,
    red50,
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
