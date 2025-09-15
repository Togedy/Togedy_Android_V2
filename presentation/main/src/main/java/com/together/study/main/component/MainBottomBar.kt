package com.together.study.main.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.main.MainTab
import com.together.study.util.NoRippleInteractionSource
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun MainBottomBar(
    isVisible: Boolean,
    tabs: ImmutableList<MainTab>,
    currentTab: MainTab?,
    onTabSelected: (MainTab) -> Unit,
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + slideIn { IntOffset(0, 0) },
        exit = fadeOut() + slideOut { IntOffset(0, 0) }
    ) {
        NavigationBar(containerColor = White) {
            tabs.forEach { itemType ->
                NavigationBarItem(
                    interactionSource = NoRippleInteractionSource,
                    selected = currentTab == itemType,
                    onClick = { onTabSelected(itemType) },
                    icon = {
                        val icon =
                            if (currentTab == itemType) itemType.selectedIcon
                            else itemType.defaultIcon

                        Icon(
                            imageVector = ImageVector.vectorResource(icon),
                            contentDescription = itemType.contentDescription,
                            modifier = Modifier.size(26.dp),
                            tint = Color.Unspecified,
                        )
                    },
                    label = {
                        val textColor =
                            if (currentTab == itemType) TogedyTheme.colors.gray700
                            else TogedyTheme.colors.gray500

                        Text(
                            text = itemType.contentDescription,
                            style = TogedyTheme.typography.body10m.copy(textColor),
                        )
                    },
                    colors = NavigationBarItemDefaults
                        .colors(
                            selectedIconColor = Color.Unspecified,
                            selectedTextColor = Color.Unspecified,
                            unselectedIconColor = Color.Unspecified,
                            unselectedTextColor = Color.Unspecified,
                            indicatorColor = White
                        )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainBottomBarPreview() {
    MainBottomBar(
        isVisible = true,
        tabs = MainTab.entries.toImmutableList(),
        currentTab = MainTab.CALENDAR,
        onTabSelected = {}
    )
}
