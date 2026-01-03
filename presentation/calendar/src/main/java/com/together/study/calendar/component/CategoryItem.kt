package com.together.study.calendar.component

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
import com.together.study.calendar.model.Category
import com.together.study.calendar.type.toCategoryColorOrDefault
import com.together.study.designsystem.R.drawable.ic_search_cancel_16
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

@Composable
internal fun CategoryItem(
    category: Category,
    onCategoryClick: () -> Unit,
    onEditClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    isCategorySelected: Boolean = false,
    isCategoryEditMode: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val categoryColor = category.categoryColor.toCategoryColorOrDefault()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(TogedyTheme.colors.gray50, RoundedCornerShape(8.dp))
            .padding(16.dp)
            .noRippleClickable {
                if (isCategoryEditMode) onEditClick()
                else onCategoryClick()
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .padding(2.dp)
                .padding(2.dp)
                .size(16.dp)
                .background(categoryColor, RoundedCornerShape(6.dp)),
        )

        Spacer(Modifier.width(8.dp))

        Box(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = category.categoryName.toString(),
                style = TogedyTheme.typography.chip14b.copy(categoryColor),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }

        if (isCategorySelected) {
//            Icon(
//                imageVector = ImageVector.vectorResource()
//            )
            Spacer(Modifier.width(4.dp))
            Box(
                // TODO: 추후 체크 icon 으로 변경
                modifier = Modifier
                    .size(24.dp)
                    .background(TogedyTheme.colors.gray500),
            )
        } else if (isCategoryEditMode) {
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
private fun CategoryItemPreview(modifier: Modifier = Modifier) {
    TogedyTheme {
        CategoryItem(
            category = Category.mock,
            isCategorySelected = false,
            onCategoryClick = {},
            modifier = modifier,
        )
    }
}