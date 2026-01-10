package com.together.study.calendar.category

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.together.study.calendar.component.CategoryItem
import com.together.study.calendar.model.Category
import com.together.study.common.state.UiState
import com.together.study.designsystem.R.drawable.ic_left_chevron
import com.together.study.designsystem.component.button.AddButton
import com.together.study.designsystem.component.dialog.TogedyBasicDialog
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

@Composable
internal fun CategoryDetailRoute(
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CategoryDetailViewModel = hiltViewModel(),
) {
    val categoryState by viewModel.categoryState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchCategoryItems()
    }

    CategoryDetailScreen(
        categoryState = categoryState,
        onBackButtonClick = onBackButtonClick,
        onAddDoneBtnClick = { viewModel.saveNewCategory(it.categoryName!!, it.categoryColor!!) },
        onEditDoneBtnClick = viewModel::updateCategory,
        onDeleteClick = viewModel::deleteCategory,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDetailScreen(
    categoryState: UiState<List<Category>>,
    onBackButtonClick: () -> Unit,
    onAddDoneBtnClick: (Category) -> Unit,
    onEditDoneBtnClick: (Category) -> Unit,
    onDeleteClick: (Long) -> Unit,
    modifier: Modifier,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isAddBottomSheetOpen by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    var isDeleteDialogOpen by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.white),
    ) {
        CategoryTopSection(
            onBackButtonClick = onBackButtonClick,
        )

        Spacer(Modifier.height(24.dp))

        Box(
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            AddButton(
                title = "카테고리 추가",
                onClick = {
                    selectedCategory = null
                    isAddBottomSheetOpen = true
                },
            )
        }

        when (categoryState) {
            is UiState.Loading -> {}
            is UiState.Success -> {
                CategoryItems(
                    categoryItems = categoryState.data,
                    onEditClick = { selectedItem ->
                        selectedCategory = selectedItem
                        isAddBottomSheetOpen = true
                    },
                    onDeleteClick = { id ->
                        selectedCategory = Category(id, "", "")
                        isDeleteDialogOpen = true
                    },
                )
            }

            is UiState.Failure -> {}
            is UiState.Empty -> {}
        }
    }

    if (isAddBottomSheetOpen) {
        CategoryDetailBottomSheet(
            sheetState = sheetState,
            category = selectedCategory,
            onDismissRequest = {
                isAddBottomSheetOpen = false
                selectedCategory = null
            },
            onDoneClick = { category ->
                isAddBottomSheetOpen = false
                if (selectedCategory == null) onAddDoneBtnClick(category)
                else onEditDoneBtnClick(category)
                selectedCategory = null
            },
        )
    }

    if (isDeleteDialogOpen) {
        TogedyBasicDialog(
            title = "카테고리 삭제",
            subTitle = {
                Text(
                    text = "정말로 삭제하시겠습니까?",
                    style = TogedyTheme.typography.body14b,
                    color = TogedyTheme.colors.gray900,
                )
            },
            buttonText = "삭제",
            onDismissRequest = { isDeleteDialogOpen = false },
            onButtonClick = {
                onDeleteClick(selectedCategory!!.categoryId!!)
                selectedCategory = null
                isDeleteDialogOpen = false
            },
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun CategoryItems(
    categoryItems: List<Category>,
    onEditClick: (Category) -> Unit,
    onDeleteClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.white)
            .padding(horizontal = 16.dp)
            .padding(top = 36.dp, bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(categoryItems) { categoryItem ->
            CategoryItem(
                category = categoryItem,
                onEditClick = { onEditClick(categoryItem) },
                onCategoryClick = { onDeleteClick(categoryItem.categoryId!!) },
                isCategoryEditMode = true,
            )
        }
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
        CategoryDetailScreen(
            categoryState = UiState.Success(Category.mockList),
            onBackButtonClick = {},
            onAddDoneBtnClick = {},
            onEditDoneBtnClick = {},
            onDeleteClick = {},
            modifier = modifier,
        )
    }
}
