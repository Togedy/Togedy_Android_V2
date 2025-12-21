package com.togehter.study.studyupdate.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.togehter.study.studyupdate.StudyUpdateTextItem
import com.together.study.designsystem.R.drawable.ic_arrow_down_24
import com.together.study.designsystem.component.TogedyBottomSheet
import com.together.study.designsystem.component.wheelpicker.PickerPosition
import com.together.study.designsystem.component.wheelpicker.TogedyScrollPicker
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun StudyUpdateMemberCount(
    selectedCount: Int?,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var isBottomSheetVisible by remember { mutableStateOf(false) }
    var tempSelectedCount by remember { mutableIntStateOf(selectedCount ?: 2) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(isBottomSheetVisible) {
        if (isBottomSheetVisible) {
            tempSelectedCount = selectedCount ?: 2
            sheetState.expand()
        }
    }

    StudyUpdateTextItem(
        modifier = modifier.padding(top = 32.dp),
        inputTitle = "최대 참가 인원",
        inputEssential = true,
        inputTitleSub = "최대 30명까지 가능"
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable {
                    isBottomSheetVisible = true
                }
                .background(
                    color = TogedyTheme.colors.white,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 20.dp, vertical = 19.dp)
        ) {
            Text(
                text = selectedCount?.let { "${it}명" } ?: "인원을 선택하세요",
                style = TogedyTheme.typography.title16sb.copy(
                    color = if (selectedCount != null) TogedyTheme.colors.black else TogedyTheme.colors.gray700
                ),
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = ImageVector.vectorResource(id = ic_arrow_down_24),
                tint = TogedyTheme.colors.gray800,
                contentDescription = null,
                modifier = Modifier.padding(horizontal = 14.dp)
            )
        }
    }

    if (isBottomSheetVisible) {
        TogedyBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                isBottomSheetVisible = false
            },
            title = "최대 참가 인원",
            showDone = true,
            onDoneClick = {
                onSelect(tempSelectedCount)
                isBottomSheetVisible = false
            },
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 22.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp, vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TogedyScrollPicker(
                    initValue = tempSelectedCount,
                    minValue = 2,
                    maxValue = 30,
                    position = PickerPosition.MIDDLE,
                    unit = "명",
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { tempSelectedCount = it }
                )
            }
        }
    }
}

