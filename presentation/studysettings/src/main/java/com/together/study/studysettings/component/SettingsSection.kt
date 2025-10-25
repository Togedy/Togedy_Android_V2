package com.together.study.studysettings.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.studysettings.main.Settings

@Composable
fun SettingsSection(
    sectionTitle: String,
    items: List<Settings>,
    isEndDividerNeeded: Boolean = true,
    modifier: Modifier = Modifier,
    horizontalPaddingModifier: Modifier = Modifier.padding(horizontal = 20.dp),
) {
    Column(
        modifier = modifier,
    ) {
        Spacer(Modifier.height(16.dp))

        Text(
            text = sectionTitle,
            style = TogedyTheme.typography.body10m,
            color = TogedyTheme.colors.gray500,
            modifier = horizontalPaddingModifier,
        )

        Spacer(Modifier.height(12.dp))

        items.forEach { item ->
            SettingsItem(
                title = item.title,
                subtitle = item.subtitle,
                icon = item.icon,
                textColor = if (item.isTextRed) TogedyTheme.colors.red else TogedyTheme.colors.gray700,
                onItemClick = { item.onClick() },
                modifier = horizontalPaddingModifier,
            )

            Spacer(Modifier.height(12.dp))
        }

        Spacer(Modifier.height(4.dp))

        if (isEndDividerNeeded) {
            HorizontalDivider(thickness = 8.dp, color = TogedyTheme.colors.gray50)
        }
    }
}

@Preview
@Composable
private fun SettingsSectionPreview() {
    TogedyTheme {
        SettingsSection(
            sectionTitle = "스터디 수정",
            items = listOf(
                Settings(title = "멤버관리", onClick = {}),
                Settings(title = "스터디 인원 수 설정", onClick = {})
            )
        )
    }
}