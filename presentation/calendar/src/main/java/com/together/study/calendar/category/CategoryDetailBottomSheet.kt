package com.together.study.calendar.category

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.calendar.model.Category
import com.together.study.calendar.type.CategoryColor
import com.together.study.calendar.type.toCategoryColorEnum
import com.together.study.calendar.type.toCategoryColorOrDefault
import com.together.study.designsystem.component.TogedyBottomSheet
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.presentation.calendar.R.drawable.ic_category_box
import com.together.study.util.noRippleClickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CategoryDetailBottomSheet(
    sheetState: SheetState,
    category: Category? = null,
    onDismissRequest: () -> Unit,
    onDoneClick: (Category) -> Unit,
    modifier: Modifier = Modifier,
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val bottomSheetHeight = screenHeight * 0.765f

    val title = if (category == null) "카테고리 추가" else "카테고리 수정"
    val textStyle = TogedyTheme.typography.title16sb

    var categoryName by remember { mutableStateOf(category?.categoryName ?: "") }
    var selectedColor by remember { mutableStateOf(category?.categoryColor ?: "CATEGORY_COLOR1") }

    TogedyBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        title = title,
        showDone = true,
        isDoneActivate = categoryName.isNotEmpty(),
        onDoneClick = { },
        modifier = modifier
            .fillMaxWidth()
            .height(bottomSheetHeight),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(ic_category_box),
                    contentDescription = null,
                    tint = selectedColor.toCategoryColorOrDefault(),
                )

                Spacer(Modifier.width(4.dp))

                BasicTextField(
                    value = categoryName,
                    onValueChange = { if (categoryName.length < 20) categoryName = it },
                    textStyle = textStyle,
                    decorationBox = { innerTextField ->
                        if (categoryName.isEmpty()) {
                            Text(
                                text = "제목을 입력하세요..",
                                style = textStyle.copy(color = TogedyTheme.colors.gray300)
                            )
                        }
                        innerTextField()
                    }
                )
            }

            Spacer(Modifier.height(24.dp))

            ColorPickerGrid(
                selectedColor = selectedColor.toCategoryColorEnum(),
                onColorSelected = { selectedColor = it.name }
            )
        }
    }
}

@Composable
fun ColorPickerGrid(
    selectedColor: CategoryColor,
    onColorSelected: (CategoryColor) -> Unit,
) {
    val colors = CategoryColor.entries.filter { it != CategoryColor.UNKNOWN_COLOR }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(TogedyTheme.colors.gray50, RoundedCornerShape(16.dp))
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
    ) {
        for (row in colors.chunked(6)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                for (color in row) {
                    ColorItem(
                        color = color,
                        isSelected = color == selectedColor,
                        onClick = { onColorSelected(color) },
                    )
                }
            }
        }
    }
}

@Composable
fun ColorItem(
    color: CategoryColor,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val categoryColor = color.color
    val borderColor = if (isSelected) categoryColor else Color.Transparent
    val borderWidth = 2.dp
    val outerBorderWidth = 2.dp
    val roundedCornerShape = RoundedCornerShape(10.dp)

    Box(
        modifier = Modifier
            .size(40.dp)
            .border(outerBorderWidth, borderColor, roundedCornerShape)
            .padding(2.dp)
            .border(borderWidth, Color.Transparent, roundedCornerShape)
            .padding(2.dp)
            .background(categoryColor, RoundedCornerShape(6.dp))
            .noRippleClickable(onClick),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun AddingCategoryBottomSheetPreview(modifier: Modifier = Modifier) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    TogedyTheme {
        CategoryDetailBottomSheet(
            sheetState = sheetState,
            category = null,
            onDismissRequest = {},
            onDoneClick = {},
            modifier = modifier,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun EditingCategoryBottomSheetPreview(modifier: Modifier = Modifier) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    TogedyTheme {
        CategoryDetailBottomSheet(
            sheetState = sheetState,
            category = Category.mock,
            onDismissRequest = {},
            onDoneClick = {},
            modifier = modifier,
        )
    }
}