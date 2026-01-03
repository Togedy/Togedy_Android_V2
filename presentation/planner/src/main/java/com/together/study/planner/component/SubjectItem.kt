package com.together.study.planner.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.R.drawable.ic_search_cancel_16
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.planner.model.PlannerSubject
import com.together.study.planner.type.toPlannerSubjectColorOrDefault
import com.together.study.util.noRippleClickable

@Composable
internal fun SubjectItem(
    plannerSubject: PlannerSubject,
    isSubjectEditMode: Boolean = false,
    onSubjectClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val subjectColor = plannerSubject.color.toPlannerSubjectColorOrDefault()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(TogedyTheme.colors.gray50, RoundedCornerShape(8.dp))
            .padding(16.dp)
            .noRippleClickable {
                if (isSubjectEditMode) onEditClick()
                else onSubjectClick()
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .padding(2.dp)
                .padding(2.dp)
                .size(16.dp)
                .background(subjectColor, RoundedCornerShape(6.dp)),
        )

        Spacer(Modifier.width(8.dp))

        Box(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = plannerSubject.name.toString(),
                style = TogedyTheme.typography.chip14b.copy(subjectColor),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }

        if (isSubjectEditMode) {
            Spacer(Modifier.width(4.dp))

            Icon(
                imageVector = ImageVector.vectorResource(ic_search_cancel_16),
                contentDescription = "삭제 버튼",
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(24.dp)
                    .noRippleClickable(onDeleteClick),
            )
        } else {
            Spacer(Modifier.width(28.dp))
        }
    }
}

@Preview
@Composable
private fun SubjectItemPreview(modifier: Modifier = Modifier) {
    TogedyTheme {
        SubjectItem(
            plannerSubject = PlannerSubject(0, "hi", "SUBJECT_COLOR1", emptyList()),
            onSubjectClick = {},
            modifier = modifier,
        )
    }
}
