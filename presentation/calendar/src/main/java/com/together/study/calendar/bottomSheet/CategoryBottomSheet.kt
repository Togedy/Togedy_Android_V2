package com.together.study.calendar.bottomSheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
    category: Category?,
    onDismissRequest: () -> Unit,
    onDoneClick: (Category) -> Unit,
    onAddCategoryClick: () -> Unit,
    onEditCategoryClick: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    modifier: Modifier = Modifier,
) {
    var selectedCategory by remember { mutableStateOf(category) }
    val categoryList = Category.mockList

    LaunchedEffect(Unit) { sheetState.expand() }

    TogedyBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        title = "카테고리",
        showDone = true,
        isDoneActivate = selectedCategory != null,
        onDoneClick = { onDoneClick(selectedCategory!!) },
        modifier = modifier.fillMaxHeight(0.528f),
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
                    isCategorySelected = categoryItem.categoryId == selectedCategory?.categoryId,
                    onCategoryClick = { selectedCategory = categoryItem },
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
            category = null,
            onDismissRequest = {},
            onDoneClick = {},
            onAddCategoryClick = {},
            onEditCategoryClick = {},
            modifier = modifier,
        )
    }
}
