package com.together.study.study.main.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.component.tabbar.StudyMainTab
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

@Composable
internal fun MainTabSection(
    selectedTab: StudyMainTab,
    mainColor: Color,
    subColor: Color,
    backgroundColor: Color,
    onTabClick: (StudyMainTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomStart,
    ) {
        Row(
            modifier = Modifier
                .padding(start = 15.dp),
        ) {
            StudyMainTab.entries.forEach {
                val isSelected = selectedTab == it
                val textColor = if (isSelected) mainColor else subColor

                TabItem(
                    tabName = it.typeName,
                    isSelected = selectedTab == it,
                    textColor = textColor,
                    backgroundColor = backgroundColor,
                    onTabClick = { onTabClick(it) },
                )
            }
        }

        HorizontalDivider(thickness = 0.8.dp, color = subColor)
    }
}

@Composable
private fun TabItem(
    tabName: String,
    isSelected: Boolean,
    textColor: Color,
    backgroundColor: Color,
    onTabClick: () -> Unit,
) {
    val density = LocalDensity.current

    val lineColor = if (isSelected) textColor else backgroundColor

    var textWidth by remember { mutableStateOf(0.dp) }
    val targetWidth: Dp = if (isSelected) textWidth + 12.dp else 0.dp

    val animatedWidth: Dp by animateDpAsState(
        targetValue = targetWidth,
        animationSpec = tween(durationMillis = 250),
        label = "UnderlineWidth"
    )

    Column(
        modifier = Modifier.noRippleClickable(onTabClick),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = tabName,
            style = TogedyTheme.typography.title16sb,
            color = textColor,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(constraints)
                    textWidth = with(density) { placeable.width.toDp() }

                    layout(placeable.width, placeable.height) {
                        placeable.placeRelative(0, 0)
                    }
                }
        )

        Spacer(Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .height(1.5.dp)
                .width(animatedWidth)
                .background(color = lineColor),
        )
    }
}
