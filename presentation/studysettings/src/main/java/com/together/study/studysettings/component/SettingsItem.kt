package com.together.study.studysettings.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.R.drawable.ic_right_chevron_green
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

@Composable
internal fun SettingsItem(
    title: String,
    subtitle: String? = null,
    @DrawableRes icon: Int? = null,
    textColor: Color = TogedyTheme.colors.gray700,
    onItemClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .noRippleClickable(onItemClick)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Text(
                text = title,
                style = TogedyTheme.typography.body13b,
                color = textColor,
            )

            if (subtitle != null) {
                Spacer(Modifier.height(4.dp))

                Text(
                    text = subtitle,
                    style = TogedyTheme.typography.body10m,
                    color = TogedyTheme.colors.gray500,
                )
            }
        }

        if (icon != null) {
            Icon(
                imageVector = ImageVector.vectorResource(icon),
                contentDescription = null,
                tint = TogedyTheme.colors.gray300,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview
@Composable
private fun SettingsItemPreview() {
    TogedyTheme {
        SettingsItem(
            title = "계정 센터",
            subtitle = "비밀번호, 배경이미지, 스터디 태그 변경",
            icon = ic_right_chevron_green,
            onItemClick = {},
        )
    }
}
