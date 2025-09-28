package com.together.study.study.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.common.type.StudySortingType
import com.together.study.designsystem.R.drawable.ic_check_green
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SortBottomSheet(
    selectedSortOption: StudySortingType,
    onDismissRequest: () -> Unit,
    onSortOptionClick: (StudySortingType) -> Unit,
    modifier: Modifier = Modifier,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = rememberModalBottomSheetState(),
        modifier = modifier,
        containerColor = TogedyTheme.colors.white,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "정렬",
                style = TogedyTheme.typography.title16sb,
                color = TogedyTheme.colors.gray800,
            )

            Spacer(Modifier.height(16.dp))

            Column(
                modifier = Modifier.padding(20.dp, 12.dp, 20.dp, 40.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                StudySortingType.entries.forEach {
                    val isSelected = it == selectedSortOption
                    val textColor =
                        if (isSelected) TogedyTheme.colors.green
                        else TogedyTheme.colors.gray500

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .noRippleClickable { onSortOptionClick(it) },
                    ) {
                        Text(
                            text = it.label,
                            style = TogedyTheme.typography.body14b,
                            color = textColor,
                        )

                        Spacer(Modifier.weight(1f))

                        if (isSelected) {
                            Icon(
                                imageVector = ImageVector.vectorResource(ic_check_green),
                                contentDescription = null,
                                tint = TogedyTheme.colors.green,
                                modifier = Modifier.size(20.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun SortBottomSheetPreview() {
    TogedyTheme {
        SortBottomSheet(
            selectedSortOption = StudySortingType.RECENT,
            onDismissRequest = {},
            onSortOptionClick = {},
        )
    }
}
