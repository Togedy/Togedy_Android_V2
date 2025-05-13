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

@Immutable
class TogedyTypography(
    val title16sb: TextStyle,
    val body14m: TextStyle,
    val body12m: TextStyle,
    val toast12sb: TextStyle,
    val toast12r: TextStyle,
    val chip14b: TextStyle,
    val chip10sb: TextStyle,
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
    title16sb = TogedyTextStyle(
        fontFamily = PretendardSemiBold,
        fontSize = 16.sp
    ),
    body14m = TogedyTextStyle(
        fontFamily = PretendardMedium,
        fontSize = 14.sp
    ),
    body12m = TogedyTextStyle(
        fontFamily = PretendardMedium,
        fontSize = 12.sp
    ),
    toast12sb = TogedyTextStyle(
        fontFamily = PretendardSemiBold,
        fontSize = 12.sp
    ),
    toast12r = TogedyTextStyle(
        fontFamily = PretendardRegular,
        fontSize = 12.sp
    ),
    chip14b = TogedyTextStyle(
        fontFamily = PretendardBold,
        fontSize = 14.sp
    ),
    chip10sb = TogedyTextStyle(
        fontFamily = PretendardSemiBold,
        fontSize = 10.sp
    ),
)

@Preview(showBackground = true)
@Composable
fun TogedyTypographyPreview() {
    TogedyTheme {
        Column {
            Text(
                "TogedyTheme",
                style = TogedyTheme.typography.title16sb
            )
            Text(
                "TogedyTheme",
                style = TogedyTheme.typography.body14m
            )
            Text(
                "TogedyTheme",
                style = TogedyTheme.typography.body12m
            )
            Text(
                "TogedyTheme",
                style = TogedyTheme.typography.toast12sb
            )
            Text(
                "TogedyTheme",
                style = TogedyTheme.typography.toast12r
            )
            Text(
                "TogedyTheme",
                style = TogedyTheme.typography.chip14b
            )
            Text(
                "TogedyTheme",
                style = TogedyTheme.typography.chip10sb
            )
        }
    }
}
