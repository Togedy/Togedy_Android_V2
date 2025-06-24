package com.together.study.calendar.category

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.calendar.component.CategoryItem
import com.together.study.calendar.model.Category
import com.together.study.designsystem.R.drawable.ic_left_chevron
import com.together.study.designsystem.component.AddButton
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun CategoryDetail(modifier: Modifier = Modifier) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isAddBottomSheetOpen by remember { mutableStateOf(false) }
    val categoryList = Category.mockList

    var selectedCategory by remember { mutableStateOf<Category?>(null) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.white)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        stickyHeader {
            CategoryTopSection(
                onBackButtonClick = {},
            )

            Spacer(Modifier.height(24.dp))

            AddButton(
                title = "카테고리 추가",
                onClick = {
                    selectedCategory = null
                    isAddBottomSheetOpen = true
                },
            )
        }

        item {
            Spacer(Modifier.height(36.dp))
        }

        items(categoryList) { categoryItem ->
            CategoryItem(
                category = categoryItem,
                onEditClick = {
                    selectedCategory = categoryItem
                    isAddBottomSheetOpen = true
                },
                onCategoryClick = { /* 카테고리 삭제 */ },
                isCategoryEditMode = true,
            )
        }

        item {
            Spacer(Modifier.height(20.dp))
        }
    }

    if (isAddBottomSheetOpen) {
        CategoryDetailBottomSheet(
            sheetState = sheetState,
            category = selectedCategory,
            onDismissRequest = { isAddBottomSheetOpen = false },
            onDoneClick = { /* 수정 APi 호출*/ },
        )
    }
}

@Composable
fun CategoryTopSection(
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(ic_left_chevron),
            contentDescription = "뒤로가기",
            tint = TogedyTheme.colors.gray700,
            modifier = Modifier.noRippleClickable(onBackButtonClick),
        )

        Text(
            text = "카테고리 편집",
            style = TogedyTheme.typography.title16sb.copy(TogedyTheme.colors.gray700),
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@Preview
@Composable
private fun CategoryDetailPreview(modifier: Modifier = Modifier) {
    TogedyTheme {
        CategoryDetail(
            modifier = modifier,
        )
    }
}