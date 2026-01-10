package com.together.study.planner.subject

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.together.study.common.state.UiState
import com.together.study.designsystem.R.drawable.ic_left_chevron
import com.together.study.designsystem.component.button.AddButton
import com.together.study.designsystem.component.dialog.TogedyBasicDialog
import com.together.study.designsystem.component.topbar.TogedyTopBar
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.planner.component.SubjectItem
import com.together.study.planner.model.PlannerSubject

@Composable
internal fun SubjectDetailRoute(
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SubjectDetailViewModel = hiltViewModel(),
) {
    val subjectState by viewModel.subjectState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchSubjectItems()
    }

    SubjectDetailScreen(
        subjectState = subjectState,
        onBackButtonClick = onBackButtonClick,
        onAddDoneBtnClick = { viewModel.saveNewSubject(it.name, it.color) },
        onEditDoneBtnClick = viewModel::updateSubject,
        onDeleteButtonClick = viewModel::deleteSubject,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SubjectDetailScreen(
    subjectState: UiState<List<PlannerSubject>>,
    onBackButtonClick: () -> Unit,
    onAddDoneBtnClick: (PlannerSubject) -> Unit,
    onEditDoneBtnClick: (PlannerSubject) -> Unit,
    onDeleteButtonClick: (Long) -> Unit,
    modifier: Modifier,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isBottomSheetOpen by remember { mutableStateOf(false) }
    var selectedSubject by remember { mutableStateOf<PlannerSubject?>(null) }
    var isDeleteDialogOpen by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TogedyTheme.colors.white),
    ) {
        TogedyTopBar(
            title = "과목 편집",
            leftIcon = ImageVector.vectorResource(ic_left_chevron),
            onLeftClicked = onBackButtonClick,
        )

        Spacer(Modifier.height(24.dp))

        Box(
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            AddButton(
                title = "과목 추가",
                onClick = {
                    selectedSubject = null
                    isBottomSheetOpen = true
                },
            )
        }

        when (subjectState) {
            is UiState.Loading -> {}
            is UiState.Success -> {
                SubjectItems(
                    subjects = subjectState.data,
                    onEditClick = { selectedItem ->
                        selectedSubject = selectedItem
                        isBottomSheetOpen = true
                    },
                    onItemDeleteClick = { id ->
                        selectedSubject = PlannerSubject(id, "", "", emptyList())
                        isDeleteDialogOpen = true
                    },
                )
            }

            is UiState.Failure -> {}
            is UiState.Empty -> {}
        }
    }

    if (isBottomSheetOpen) {
        SubjectDetailBottomSheet(
            sheetState = sheetState,
            plannerSubject = selectedSubject,
            onDismissRequest = {
                isBottomSheetOpen = false
                selectedSubject = null
            },
            onDoneClick = { subject ->
                isBottomSheetOpen = false
                if (selectedSubject == null) onAddDoneBtnClick(subject)
                else onEditDoneBtnClick(subject)
                selectedSubject = null
            },
        )
    }

    if (isDeleteDialogOpen) {
        TogedyBasicDialog(
            title = "과목 삭제",
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
                onDeleteButtonClick(selectedSubject!!.id!!)
                isDeleteDialogOpen = false
            },
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun SubjectItems(
    subjects: List<PlannerSubject>,
    onEditClick: (PlannerSubject) -> Unit,
    onItemDeleteClick: (Long) -> Unit,
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
        items(subjects) { subjectItem ->
            SubjectItem(
                plannerSubject = subjectItem,
                isSubjectEditMode = true,
                onEditClick = { onEditClick(subjectItem) },
                onDeleteClick = { onItemDeleteClick(subjectItem.id!!) }
            )
        }
    }
}

@Preview
@Composable
private fun SubjectDetailPreview(modifier: Modifier = Modifier) {
    TogedyTheme {
        SubjectDetailScreen(
            subjectState = UiState.Success(emptyList()),
            onBackButtonClick = {},
            onAddDoneBtnClick = {},
            onEditDoneBtnClick = {},
            onDeleteButtonClick = {},
            modifier = modifier,
        )
    }
}
