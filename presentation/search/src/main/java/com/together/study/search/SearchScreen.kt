package com.together.study.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.together.study.designsystem.R
import com.together.study.designsystem.component.TogedyBottomSheet
import com.together.study.designsystem.component.TogedyScheduleChip
import com.together.study.designsystem.component.TogedySearchBar
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.search.component.SearchSelectorChip
import com.together.study.search.component.SearchSelectorHeader
import com.together.study.util.noRippleClickable
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier,
    onBackButtonClicked: () -> Unit = {},
    viewModel: SearchViewModel = viewModel(),
) {
    val searchValue by viewModel.searchQuery.collectAsStateWithLifecycle()
    val filteredList by viewModel.filteredList.collectAsStateWithLifecycle()

    val coroutineScope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var selectedData by remember { mutableStateOf<SearchScheduleData?>(null) }
    var isSheetVisible by remember { mutableStateOf(false) }

    val onCloseBottomSheet: () -> Unit = {
        coroutineScope.launch {
            isSheetVisible = false
            sheetState.hide()
        }
    }

    val onOpenBottomSheet: () -> Unit = {
        coroutineScope.launch {
            isSheetVisible = true
            sheetState.show()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = TogedyTheme.colors.gray50)
            .padding(top = 20.dp)
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_left_24),
                contentDescription = null,
                modifier = Modifier.padding(end = 4.dp)
                    .noRippleClickable (onBackButtonClicked)
            )
            TogedySearchBar(
                value = searchValue,
                onValueChange = { viewModel.onSearchQueryChanged(it) }
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredList.size) { index ->
                val data = filteredList[index]
                SearchSelectorChip(
                    data = data,
                    onSelectorClicked = {
                        selectedData = data
                        onOpenBottomSheet()
                    }
                )
            }
        }
    }

    if (isSheetVisible) {
        TogedyBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                onCloseBottomSheet()
            },
            title = "대학일정",
            showDone = false,
            onDoneClick = {
                onCloseBottomSheet()
            }
        ) {
            BottomSheetContent(
                data = selectedData
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContent(
    data: SearchScheduleData?,
    onclickScheduleAdd: (Int) -> Unit = {}
) {
    if (data == null) return

    val admissionList = data.admissionList
    var selectedIndex by remember { mutableIntStateOf(0) }
    val selectedAdmission = admissionList.getOrNull(selectedIndex)

    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        SearchSelectorHeader(
            admissionType = data.admissionType,
            universityName = data.universityName,
            isAdded = data.isAdded,
            onClickScheduleAdd = { onclickScheduleAdd(selectedIndex) }
        )

        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded }
        ) {
            Box(
                modifier = Modifier
                    .menuAnchor(type, enabled)
                    .fillMaxWidth()
                    .padding(top = 12.dp)
                    .noRippleClickable {
                        isExpanded = true
                    }
                    .border(
                        width = 1.dp,
                        color = TogedyTheme.colors.gray300,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedAdmission?.admissionMethod ?: "전형 선택",
                        style = TogedyTheme.typography.body14m.copy(
                            color = TogedyTheme.colors.gray300
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    Icon(
                        painter = painterResource(R.drawable.ic_drop_down_24),
                        contentDescription = "드롭다운 이미지"
                    )
                }
            }

            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = {
                    isExpanded = false
                },
                containerColor = TogedyTheme.colors.white,
                shadowElevation = 1.dp

            ) {
                admissionList.forEachIndexed { index, admission ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = admission.admissionMethod,
                                style = TogedyTheme.typography.toast12sb.copy(
                                    TogedyTheme.colors.gray600
                                )
                            )
                        },
                        onClick = {
                            selectedIndex = index
                            isExpanded = false
                        },
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 25.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            selectedAdmission?.universityScheduleList?.let { scheduleList ->
                items(scheduleList.size) { index ->
                    val schedule = scheduleList[index]
                    TogedyScheduleChip(
                        typeStatus = when (schedule.admissionStage) {
                            "원서접수" -> 1
                            "서류제출" -> 2
                            "합격발표" -> 3
                            else -> 0
                        },
                        scheduleName = data.universityName,
                        scheduleType = selectedAdmission.admissionMethod,
                        scheduleStartTime = schedule.startDate,
                        scheduleEndTime = schedule.endDate
                    )
                }
            }
        }
    }
}
