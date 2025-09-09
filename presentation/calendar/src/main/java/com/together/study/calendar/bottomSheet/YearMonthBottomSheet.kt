package com.together.study.calendar.bottomSheet

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.component.TogedyBottomSheet
import com.together.study.designsystem.component.wheelpicker.TogedyYearMonthPicker
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun YearMonthBottomSheet(
    initDate: LocalDate,
    onDismissRequest: () -> Unit,
    onDoneClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
) {
    var currentDate = initDate

    TogedyBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        showDone = true,
        onDoneClick = { onDoneClick(currentDate) },
        modifier = modifier.padding(bottom = 20.dp),
    ) {
        TogedyYearMonthPicker(
            initYear = initDate.year,
            initMonth = initDate.monthValue,
            onValueChange = { year, month ->
                currentDate = LocalDate.of(year, month, 1)
            }
        )
    }
}
