package com.together.study.mypage.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.component.TogedyBottomSheet
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ImageEditBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    onDismissRequest: () -> Unit,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
) {

    LaunchedEffect(Unit) { sheetState.expand() }

    TogedyBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
        ) {
            SelectedItem(
                itemName = "삭제",
                textColor = TogedyTheme.colors.red,
                onClick = onDeleteClick,
            )
            SelectedItem(
                itemName = "이미지 선택",
                textColor = TogedyTheme.colors.gray800,
                onClick = onEditClick,
            )
            SelectedItem(
                itemName = "취소",
                textColor = TogedyTheme.colors.gray500,
                showDivider = false,
                onClick = onDismissRequest,
            )
        }
    }
}

@Composable
private fun SelectedItem(
    itemName: String,
    textColor: Color,
    modifier: Modifier = Modifier,
    showDivider: Boolean = true,
    onClick: () -> Unit,
) {
    Text(
        text = itemName,
        style = TogedyTheme.typography.body14b,
        color = textColor,
        textAlign = TextAlign.Center,
        modifier = modifier
            .noRippleClickable(onClick)
            .fillMaxWidth()
            .padding(vertical = 20.dp),
    )

    if (showDivider) {
        HorizontalDivider(thickness = 1.dp, color = TogedyTheme.colors.gray100)
    }}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun ImageEditBottomSheetPreview() {
    TogedyTheme {
        ImageEditBottomSheet(
            onDismissRequest = {},
            onDeleteClick = {},
            onEditClick = {},
        )
    }
}
