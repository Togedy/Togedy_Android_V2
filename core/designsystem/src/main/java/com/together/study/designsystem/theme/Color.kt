package com.together.study.designsystem.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

val green = Color(0xFF11BC78)
val green_bg = Color(0xFFEEFAF6)

val white = Color(0xFFFFFFFF)
val bg = Color(0xFFF8F8FA)
val button_bg = Color(0xFFF2F3F5)
val text = Color(0xFF4B525E)
val button_text = Color(0xFF5D626B)
val picker_text = Color(0xFF8E9197)
val sub_text = Color(0xFFB4B8BF)
val icon = Color(0xFFD5D9Df)
val black = Color(0x00000000)

@Stable
class TogedyColors(
    green: Color,
    green_bg: Color,
    white : Color,
    bg: Color,
    button_bg: Color,
    text: Color,
    button_text: Color,
    picker_text: Color,
    sub_text: Color,
    icon: Color,
    black: Color
) {
    var green by mutableStateOf(green)
        private set
    var green_bg by mutableStateOf(green_bg)
        private set
    var white by mutableStateOf(white)
        private set
    var bg by mutableStateOf(bg)
        private set
    var button_bg by mutableStateOf(button_bg)
        private set
    var text by mutableStateOf(text)
        private set
    var button_text by mutableStateOf(button_text)
        private set
    var picker_text by mutableStateOf(picker_text)
        private set
    var sub_text by mutableStateOf(sub_text)
        private set
    var icon by mutableStateOf(icon)
        private set
    var black by mutableStateOf(black)
        private set

    fun copy(): TogedyColors = TogedyColors(
        green,
        green_bg,
        white,
        bg,
        button_bg,
        text,
        button_text,
        picker_text,
        sub_text,
        icon,
        black
    )

    fun update(other: TogedyColors) {
        green = other.green
        green_bg = other.green_bg
        white = other.white
        bg = other.bg
        button_bg = other.button_bg
        text = other.text
        button_text = other.button_text
        picker_text = other.picker_text
        sub_text = other.sub_text
        icon = other.icon
        black = other.black
    }
}

fun TogedyLightColors(
    Green: Color = green,
    GREEN_BG: Color = green_bg,
    WHITE: Color = white,
    BG: Color = bg,
    BUTTON_BG: Color = button_bg,
    TEXT: Color = text,
    BUTTON_TEXT: Color = button_text,
    PICKER_TEXT: Color = picker_text,
    SUB_TEXT: Color = sub_text,
    ICON: Color = icon,
    BLACK: Color = black
) = TogedyColors(
    Green,
    GREEN_BG,
    WHITE,
    BG,
    BUTTON_BG,
    TEXT,
    BUTTON_TEXT,
    PICKER_TEXT,
    SUB_TEXT,
    ICON,
    BLACK
)
