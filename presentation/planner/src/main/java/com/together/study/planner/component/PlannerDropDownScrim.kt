package com.together.study.planner.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
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
                focusable = true,
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
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
        modifier = modifier
            .background(TogedyTheme.colors.white, RoundedCornerShape(8.dp)),
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
    DropdownMenuItem(
        text = {
            Text(
                text = text,
                style = TogedyTheme.typography.body13b,
                color = TogedyTheme.colors.gray700,
                modifier = Modifier.padding(start = 10.dp)
            )
        },
        trailingIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(imageResId),
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(16.dp)
            )
        },
        onClick = onClick,
        modifier = modifier.background(color = TogedyTheme.colors.white),
        contentPadding = PaddingValues(0.dp),
    )
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