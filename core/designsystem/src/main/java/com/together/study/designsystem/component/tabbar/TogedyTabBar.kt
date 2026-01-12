package com.together.study.designsystem.component.tabbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

@Composable
fun <T : TabType> TogedyTabBar(
    tabList: List<T>,
    selectedTab: T,
    onTabChange: (T) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier.fillMaxWidth()) {
        tabList.forEach { tab ->
            TabBlock(
                tabName = tab.typeName,
                selected = tab == selectedTab,
                onTabClick = { onTabChange(tab) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun TabBlock(
    tabName: String,
    selected: Boolean,
    onTabClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val tabStyle =
        if (selected) TogedyTheme.typography.body14b else TogedyTheme.typography.body14m
    val tabColor =
        if (selected) TogedyTheme.colors.gray800 else TogedyTheme.colors.gray600
    val barColor =
        if (selected) TogedyTheme.colors.gray900 else TogedyTheme.colors.white

    Column(
        modifier = modifier.noRippleClickable(onTabClick),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(modifier = Modifier.padding(vertical = 10.dp)) {
            Text(
                text = tabName,
                style = tabStyle,
                color = tabColor
            )
        }

        HorizontalDivider(thickness = 2.dp, color = barColor)
    }
}

@Preview(showBackground = true)
@Composable
private fun TogedyTabBarPreview(modifier: Modifier = Modifier) {
    var selected by remember { mutableStateOf(StudyDetailTab.MEMBER) }
    TogedyTheme {
        TogedyTabBar(
            tabList = StudyDetailTab.entries,
            selectedTab = selected,
            onTabChange = { selected = it },
            modifier = modifier,
        )
    }
}
