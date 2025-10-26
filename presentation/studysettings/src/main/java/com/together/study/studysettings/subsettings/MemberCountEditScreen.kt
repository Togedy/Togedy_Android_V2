package com.together.study.studysettings.subsettings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.R.drawable.ic_down_chevron_16
import com.together.study.designsystem.R.drawable.ic_left_chevron
import com.together.study.designsystem.component.TogedyBottomSheet
import com.together.study.designsystem.component.topbar.TogedyTopBar
import com.together.study.designsystem.component.wheelpicker.PickerPosition
import com.together.study.designsystem.component.wheelpicker.TogedyScrollPicker
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemberCountEditRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isBottomSheetVisible by remember { mutableStateOf(true) }
    var memberCount = 10
    var selectedCount by remember { mutableIntStateOf(memberCount) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.gray50)
            .padding(top = 22.dp),
    ) {
        TogedyTopBar(
            title = "스터디 인원수 설정",
            leftIcon = ImageVector.vectorResource(id = ic_left_chevron),
            modifier = Modifier.padding(bottom = 4.dp),
            onLeftClicked = onBackClick,
        )

        Spacer(Modifier.height(20.dp))

        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "참가인원",
                    style = TogedyTheme.typography.body14b,
                    color = TogedyTheme.colors.gray800,
                )

                Spacer(Modifier.width(4.dp))

                Text(
                    text = "인원을 현재보다 늘리는 것만 가능해요",
                    style = TogedyTheme.typography.body10m,
                    color = TogedyTheme.colors.gray500,
                )
            }

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        TogedyTheme.colors.white,
                        RoundedCornerShape(8.dp)
                    )
                    .noRippleClickable { isBottomSheetVisible = true }
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "3명",
                    style = TogedyTheme.typography.title16sb,
                    color = TogedyTheme.colors.gray700,
                )

                Icon(
                    imageVector = ImageVector.vectorResource(ic_down_chevron_16),
                    contentDescription = null,
                    tint = TogedyTheme.colors.gray800,
                )
            }
        }
    }

    if (isBottomSheetVisible) {
        TogedyBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { isBottomSheetVisible = false },
            title = "스터디 인원",
            showDone = true,
            isDoneActivate = memberCount <= selectedCount,
            onDoneClick = {
                // 수정 API
                memberCount = selectedCount
            },
            modifier = modifier,
        ) {
            TogedyScrollPicker(
                initValue = memberCount,
                minValue = 2,
                maxValue = 30,
                position = PickerPosition.START,
                modifier = modifier,
            )
        }
    }
}

@Preview
@Composable
private fun MemberCountEditRoutePreview() {
    TogedyTheme {
        MemberCountEditRoute(
            onBackClick = {},
        )
    }
}
