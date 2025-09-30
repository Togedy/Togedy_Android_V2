package com.together.study.study.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.component.textchip.TogedyClickableTextChip
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.study.type.StudyTagType

@Composable
internal fun TagFilterSection(
    selectedFilter: List<StudyTagType>,
    modifier: Modifier = Modifier,
    onFilterClick: (StudyTagType) -> Unit,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
    ) {
        item {
            Spacer(Modifier.width(8.dp))
        }

        items(StudyTagType.entries) { tag ->
            TogedyClickableTextChip(
                text = tag.icon + tag.label,
                selected = tag in selectedFilter,
                onClick = { onFilterClick(tag) },
            )
        }

        item {
            Spacer(Modifier.width(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TagFilterSectionPreview() {
    TogedyTheme {
        TagFilterSection(
            selectedFilter = listOf(StudyTagType.ENTIRE),
            modifier = Modifier,
            onFilterClick = { },
        )
    }
}
