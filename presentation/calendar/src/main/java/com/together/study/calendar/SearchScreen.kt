package com.together.study.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.together.study.calendar.component.SearchSelectorChip
import com.together.study.designsystem.component.TogedyBottomSheet
import com.together.study.designsystem.component.TogedySearchBar
import com.together.study.designsystem.R
import com.together.study.designsystem.component.TogedyScheduleChip
import com.together.study.designsystem.theme.TogedyTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarSearchScreen() {
    var searchValue by remember { mutableStateOf("") }

    val allList = remember { dummyScheduleList() }
    var filteredList by remember {
        mutableStateOf(
            allList.sortedByDescending { it.isAdded }
        )
    }

    val coroutineScope = rememberCoroutineScope()
    var searchJob by remember { mutableStateOf<Job?>(null) }

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
        modifier = Modifier
            .fillMaxSize()
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
            )
            TogedySearchBar(
                value = searchValue,
                onValueChange = {
                    searchValue = it

                    searchJob?.cancel()
                    searchJob = coroutineScope.launch {
                        delay(300)
                        filteredList = allList
                            .filter { item ->
                                item.universityName.contains(searchValue, ignoreCase = true)
                            }
                            .sortedByDescending { it.isAdded }
                    }
                }
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
            showDone = true,
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
fun BottomSheetContent(data: SearchScheduleData?) {
    Column(

    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp, bottom = 25.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            data?.admissionList?.forEach { admission ->
                item {
                    Text(
                        text = admission.admissionMethod,
                        style = TogedyTheme.typography.body14m,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                items(admission.universityScheduleList.size) { index ->
                    val schedule = admission.universityScheduleList[index]
                    TogedyScheduleChip(
                        typeStatus = when (schedule.admissionStage) {
                            "원서접수" -> 1
                            "서류제출" -> 2
                            "합격발표" -> 3
                            else -> 0
                        },
                        scheduleName = data.universityName,
                        scheduleType = admission.admissionMethod,
                        scheduleStartTime = schedule.startDate,
                        scheduleEndTime = schedule.endDate
                    )
                }
            }
        }
    }
}
