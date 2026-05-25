package com.together.study.planner.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.together.study.designsystem.R.drawable.ic_add_24
import com.together.study.designsystem.R.drawable.ic_list
import com.together.study.designsystem.R.drawable.ic_share_20
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

@Composable
internal fun PlannerDropDownScrim(
    expanded: Boolean,
    modifier: Modifier = Modifier,
    scrimAlpha: Float = 0.1f,
    onDismissRequest: () -> Unit,
    onPlusPlannerSubjectClick: () -> Unit,
    onEditPlannerSubjectClick: () -> Unit,
    onShareButtonClick: () -> Unit,
) {
    if (expanded) {
        Popup(
            onDismissRequest = onDismissRequest,
            properties = PopupProperties(
                focusable = false,
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(TogedyTheme.colors.black.copy(alpha = scrimAlpha))
                    .noRippleClickable(onDismissRequest),
            )
        }
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier.removeVerticalPadding(6.dp),
        shape = RoundedCornerShape(8.dp),
        containerColor = TogedyTheme.colors.white,
    ) {
        Column {
            PlannerDropDownScrimItem(
                text = "과목 추가",
                imageResId = ic_add_24,
                iconColor = TogedyTheme.colors.green,
                onClick = onPlusPlannerSubjectClick,
            )

            HorizontalDivider(color = TogedyTheme.colors.gray200)

            PlannerDropDownScrimItem(
                text = "과목 관리",
                imageResId = ic_list,
                iconColor = TogedyTheme.colors.gray700,
                onClick = onEditPlannerSubjectClick,
            )

            HorizontalDivider(color = TogedyTheme.colors.gray200)

            PlannerDropDownScrimItem(
                text = "이미지로 공유",
                imageResId = ic_share_20,
                iconColor = TogedyTheme.colors.gray500,
                onClick = onShareButtonClick,
            )
        }
    }
}

@Composable
fun PlannerDropDownScrimItem(
    text: String,
    imageResId: Int,
    iconColor: Color = TogedyTheme.colors.gray800,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(color = TogedyTheme.colors.white)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            style = TogedyTheme.typography.body13b,
            color = TogedyTheme.colors.gray700,
        )

        Spacer(modifier = Modifier.width(9.dp))

        Icon(
            imageVector = ImageVector.vectorResource(imageResId),
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(16.dp),
        )
    }
}

private fun Modifier.removeVerticalPadding(padding: Dp) = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    val offsetPx = padding.roundToPx()
    layout(placeable.width, placeable.height - offsetPx * 2) {
        placeable.place(0, -offsetPx)
    }
}

@Preview()
@Composable
private fun PlannerDropDownScrimPreview() {
    TogedyTheme {
        PlannerDropDownScrim(
            expanded = true,
            onDismissRequest = {},
            onPlusPlannerSubjectClick = {},
            onEditPlannerSubjectClick = {},
            onShareButtonClick = {},
        )
    }
}