package com.together.study.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.mypage.type.MenuItemUi
import kotlin.enums.EnumEntries

@Composable
internal fun <T> MenuList(
    title: String,
    items: EnumEntries<T>,
    onClick: (T) -> Unit,
) where T : Enum<T>, T : MenuItemUi {
    Column(
        modifier = Modifier
            .padding(top = 20.dp)
            .background(TogedyTheme.colors.white, RoundedCornerShape(12.dp))
            .padding(vertical = 13.dp, horizontal = 18.dp),
    ) {
        Text(
            text = title,
            style = TogedyTheme.typography.chip14b,
            color = TogedyTheme.colors.gray500,
        )

        items.forEachIndexed { index, item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(item) }
                    .padding(vertical = 16.dp, horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(item.iconRes),
                    contentDescription = item.title,
                    modifier = Modifier.size(20.dp),
                    tint = TogedyTheme.colors.gray600,
                )

                Spacer(Modifier.width(12.dp))

                Text(
                    text = item.title,
                    style = TogedyTheme.typography.body14b,
                    color = TogedyTheme.colors.gray800,
                )
            }

            Spacer(Modifier.width(12.dp))

            if (index != items.lastIndex)
                HorizontalDivider(color = TogedyTheme.colors.gray200)
        }
    }
}
