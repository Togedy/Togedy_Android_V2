package com.together.study.designsystem.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.together.study.designsystem.R

val PretendardBold = FontFamily(Font(R.font.pretendard_bold, FontWeight.Bold))
val PretendardSemiBold = FontFamily(Font(R.font.pretendard_semibold, FontWeight.SemiBold))
val PretendardMedium = FontFamily(Font(R.font.pretendard_medium, FontWeight.Medium))
val PretendardRegular = FontFamily(Font(R.font.pretendard_regular, FontWeight.Normal))
val PretendardLight = FontFamily(Font(R.font.pretendard_light, FontWeight.Light))

@Immutable
class TogedyTypography(
    val title18b: TextStyle,
    val title16sb: TextStyle,
    val body14b: TextStyle,
    val body14m: TextStyle,
    val body13b: TextStyle,
    val body13m: TextStyle,
    val body12m: TextStyle,
    val body10m: TextStyle,
    val toast13sb: TextStyle,
    val toast12sb: TextStyle,
    val toast12r: TextStyle,
    val chip14b: TextStyle,
    val chip10sb: TextStyle,
    val time40l: TextStyle,
)

private fun TogedyTextStyle(
    fontFamily: FontFamily,
    fontSize: TextUnit,
    lineHeight: TextUnit = 1.28.em,
    letterSpacing: TextUnit = 0.02.em,
): TextStyle = TextStyle(
    fontFamily = fontFamily,
    fontSize = fontSize,
    lineHeight = lineHeight,
    letterSpacing = letterSpacing,
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.None
    ),
)

fun TogedyTypography() = TogedyTypography(
    title18b = TogedyTextStyle(
        fontFamily = PretendardBold,
        fontSize = 18.sp,
        lineHeight = 22.sp,
    ),
    title16sb = TogedyTextStyle(
        fontFamily = PretendardSemiBold,
        fontSize = 16.sp,
        lineHeight = 22.sp,
    ),
    body14b = TogedyTextStyle(
        fontFamily = PretendardBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    body14m = TogedyTextStyle(
        fontFamily = PretendardMedium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    body13b = TogedyTextStyle(
        fontFamily = PretendardBold,
        fontSize = 13.sp,
        lineHeight = 29.sp,
    ),
    body13m = TogedyTextStyle(
        fontFamily = PretendardMedium,
        fontSize = 13.sp,
        lineHeight = 29.sp,
    ),
    body12m = TogedyTextStyle(
        fontFamily = PretendardMedium,
        fontSize = 12.sp,
        lineHeight = 18.sp,
    ),
    body10m = TogedyTextStyle(
        fontFamily = PretendardMedium,
        fontSize = 10.sp,
        lineHeight = 12.sp,
    ),
    toast13sb = TogedyTextStyle(
        fontFamily = PretendardSemiBold,
        fontSize = 13.sp,
        lineHeight = 15.sp,
    ),
    toast12sb = TogedyTextStyle(
        fontFamily = PretendardSemiBold,
        fontSize = 12.sp,
        lineHeight = 14.sp,
    ),
    toast12r = TogedyTextStyle(
        fontFamily = PretendardRegular,
        fontSize = 12.sp,
        lineHeight = 14.sp,
    ),
    chip14b = TogedyTextStyle(
        fontFamily = PretendardBold,
        fontSize = 14.sp,
        lineHeight = 18.sp,
    ),
    chip10sb = TogedyTextStyle(
        fontFamily = PretendardSemiBold,
        fontSize = 10.sp,
        lineHeight = 12.sp,
    ),
    time40l = TogedyTextStyle(
        fontFamily = PretendardLight,
        fontSize = 40.sp,
        lineHeight = 48.sp,
    )
)

@Preview(showBackground = true)
@Composable
fun TogedyTypographyPreview() {
    TogedyTheme {
        Column {
            Text("title18b - TogedyTheme", style = TogedyTheme.typography.title18b)
            Text("title16sb - TogedyTheme", style = TogedyTheme.typography.title16sb)
            Text("body14b - TogedyTheme", style = TogedyTheme.typography.body14b)
            Text("body14m - TogedyTheme", style = TogedyTheme.typography.body14m)
            Text("body13b - TogedyTheme", style = TogedyTheme.typography.body13b)
            Text("body13m - TogedyTheme", style = TogedyTheme.typography.body13m)
            Text("body12m - TogedyTheme", style = TogedyTheme.typography.body12m)
            Text("body10m - TogedyTheme", style = TogedyTheme.typography.body10m)
            Text("toast13sb - TogedyTheme", style = TogedyTheme.typography.toast13sb)
            Text("toast12sb - TogedyTheme", style = TogedyTheme.typography.toast12sb)
            Text("toast12r - TogedyTheme", style = TogedyTheme.typography.toast12r)
            Text("chip14b - TogedyTheme", style = TogedyTheme.typography.chip14b)
            Text("chip10sb - TogedyTheme", style = TogedyTheme.typography.chip10sb)
            Text("time40l - TogedyTheme", style = TogedyTheme.typography.time40l)
        }
    }
}
