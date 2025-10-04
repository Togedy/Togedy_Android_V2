package com.together.study.study.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.together.study.common.type.study.StudySortingType
import com.together.study.designsystem.R.drawable.ic_circle_gray_24
import com.together.study.designsystem.R.drawable.ic_down_chevron_16
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

@Composable
fun SortingFilterSection(
    selectedSortingType: StudySortingType,
    isJoinable: Boolean,
    isChallenge: Boolean,
    modifier: Modifier = Modifier,
    onSortingClick: () -> Unit,
    onJoinableClick: () -> Unit,
    onChallengeClick: () -> Unit,
) {
    Row(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        Sorting(
            selected = selectedSortingType,
            onSortingClick = onSortingClick,
        )

        Spacer(Modifier.weight(1f))

        TextFilter(
            text = "참여가능만",
            isSelected = isJoinable,
            onClick = onJoinableClick,
        )

        Spacer(Modifier.width(4.dp))

        TextFilter(
            text = "챌린지",
            isSelected = isChallenge,
            onClick = onChallengeClick,
        )
    }
}

@Composable
private fun Sorting(
    selected: StudySortingType,
    modifier: Modifier = Modifier,
    onSortingClick: () -> Unit,
) {
    Row(
        modifier = modifier.noRippleClickable(onSortingClick),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = selected.label,
            style = TogedyTheme.typography.body13b,
            color = TogedyTheme.colors.gray800,
        )

        Icon(
            imageVector = ImageVector.vectorResource(ic_down_chevron_16),
            contentDescription = null,
            tint = TogedyTheme.colors.gray800,
        )
    }
}

@Composable
private fun TextFilter(
    text: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val icon =
        if (isSelected) ic_circle_gray_24
        else ic_circle_gray_24

    Row(
        modifier = modifier.noRippleClickable(onClick),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(icon),
            contentDescription = null,
            tint = Color.Unspecified,
        )

        Text(
            text = text,
            style = TogedyTheme.typography.body12m,
            color = TogedyTheme.colors.gray600,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SortingFilterSectionPreview() {
    TogedyTheme {
        SortingFilterSection(
            selectedSortingType = StudySortingType.RECENT,
            isJoinable = true,
            isChallenge = false,
            modifier = Modifier,
            onSortingClick = { },
            onJoinableClick = { },
            onChallengeClick = { },
        )
    }
}
