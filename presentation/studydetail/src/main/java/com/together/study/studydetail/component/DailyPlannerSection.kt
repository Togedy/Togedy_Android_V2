package com.together.study.studydetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.component.button.TogedyToggleButton
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

@Composable
internal fun PlannerVisibleToggle(
    isPlannerVisible: Boolean,
    modifier: Modifier = Modifier,
    onPlannerVisibleToggleClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .background(
                TogedyTheme.colors.gray100,
                RoundedCornerShape(8.dp)
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
                .background(
                    TogedyTheme.colors.gray100,
                    RoundedCornerShape(8.dp)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "다른 멤버에게 공개할래요",
                style = TogedyTheme.typography.chip10sb,
                color = TogedyTheme.colors.gray900,
            )

            TogedyToggleButton(
                isToggleOn = isPlannerVisible,
                onToggleClick = onPlannerVisibleToggleClick,
            )
        }
    }
}

@Composable
internal fun PlannerEditButton(
    onPlannerEditClick: () -> Unit,
) {
    Spacer(Modifier.height(10.dp))

    Text(
        text = "플래너 수정하기",
        style = TogedyTheme.typography.body14m,
        color = TogedyTheme.colors.white,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .background(
                color = TogedyTheme.colors.black,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .noRippleClickable(onPlannerEditClick),
    )
}

@Preview
@Composable
private fun PlannerVisibleTogglePreview() {
    TogedyTheme {
        PlannerVisibleToggle(
            isPlannerVisible = true,
            onPlannerVisibleToggleClick = {},
        )
    }
}

@Preview
@Composable
private fun PlannerEditButtonPreview() {
    TogedyTheme {
        PlannerEditButton(onPlannerEditClick = {})
    }
}
