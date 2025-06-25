package com.together.study.calendar.bottomSheet

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.component.TogedyBottomSheet
import com.together.study.designsystem.theme.TogedyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MemoBottomSheet(
    scheduleMemo: String,
    onValueChange: (String) -> Unit,
    onDismissRequest: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(Unit) { sheetState.expand() }

    TogedyBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        title = "메모",
        showDone = true,
        isDoneActivate = true,
        onDoneClick = onDismissRequest,
        modifier = modifier.fillMaxHeight(0.62f),
    ) {
        BasicTextField(
            value = scheduleMemo,
            onValueChange = {
                if (it.length < 30) {
                    onValueChange(it)
                } else {
                    // TODO : 토스트 띄우기
                }
            },
            textStyle = TogedyTheme.typography.body14m,
            decorationBox = { innerTextField ->
                if (scheduleMemo.isEmpty()) {
                    Text(
                        text = "메모를 입력하세요.(최대 30자)",
                        style = TogedyTheme.typography.body14m.copy(TogedyTheme.colors.gray300)
                    )
                }
                innerTextField()
            },
            modifier = Modifier.padding(16.dp)
        )
    }
}
