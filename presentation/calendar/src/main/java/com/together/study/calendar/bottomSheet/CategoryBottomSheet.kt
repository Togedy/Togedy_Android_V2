package com.together.study.calendar.bottomSheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.calendar.component.CategoryItem
import com.together.study.calendar.model.Category
import com.together.study.designsystem.component.AddButton
import com.together.study.designsystem.component.TogedyBottomSheet
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CategoryBottomSheet(
    sheetState: SheetState,
    categoryId: Long?,
    onDismissRequest: () -> Unit,
    onDoneClick: (Long) -> Unit,
    onAddCategoryClick: () -> Unit,
    onEditCategoryClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val bottomSheetHeight = screenHeight * 0.5275f

    var selectedCategoryId by remember { mutableStateOf(categoryId) }
    val categoryList = Category.mockList

    TogedyBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        title = "카테고리",
        showDone = true,
        isDoneActivate = selectedCategoryId != null,
        onDoneClick = { onDoneClick(selectedCategoryId!!) },
        modifier = modifier
            .fillMaxWidth()
            .height(bottomSheetHeight),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item {
                AddButton(
                    title = "카테고리 추가",
                    onClick = onAddCategoryClick,
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                ) {
                    Spacer(Modifier.weight(1f))

                    Text(
                        text = "카테고리 편집 >",
                        style = TogedyTheme.typography.toast12sb.copy(TogedyTheme.colors.green),
                        modifier = Modifier.noRippleClickable(onEditCategoryClick),
                    )
                }
            }

            items(categoryList) { categoryItem ->
                CategoryItem(
                    category = categoryItem,
                    isCategorySelected = categoryItem.categoryId == selectedCategoryId,
                    onCategoryClick = { selectedCategoryId = categoryItem.categoryId },
                )
            }

            item {
                Spacer(Modifier.height(20.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun CategoryBottomSheetPreview(modifier: Modifier = Modifier) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    TogedyTheme {
        CategoryBottomSheet(
            sheetState = sheetState,
            categoryId = null,
            onDismissRequest = {},
            onDoneClick = {},
            onAddCategoryClick = {},
            onEditCategoryClick = {},
            modifier = modifier,
        )
    }
}
