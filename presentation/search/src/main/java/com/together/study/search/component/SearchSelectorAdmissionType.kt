package com.together.study.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.search.type.AdmissionType
import com.together.study.util.noRippleClickable

@Composable
fun SearchSelectorAdmissionType(
    selectedType: AdmissionType,
    onSelect: (AdmissionType) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = AdmissionType.entries

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = TogedyTheme.colors.gray100,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(vertical = 4.dp, horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items.forEach { type ->
            val isSelected = selectedType == type
            Box(
                modifier = Modifier
                    .weight(1f)
                    .shadow(
                        elevation = if (isSelected) 4.dp else 0.dp,
                        shape = RoundedCornerShape(8.dp),
                    )
                    .background(
                        color = if (isSelected) TogedyTheme.colors.white else TogedyTheme.colors.gray100,
                        shape = RoundedCornerShape(8.dp),
                    )
                    .padding(vertical = 3.dp)
                    .noRippleClickable { onSelect(type) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = type.admissionName,
                    style = TogedyTheme.typography.title16sb.copy(
                        color = if (isSelected) TogedyTheme.colors.gray800 else TogedyTheme.colors.gray600
                    )
                )
            }
        }
    }
}