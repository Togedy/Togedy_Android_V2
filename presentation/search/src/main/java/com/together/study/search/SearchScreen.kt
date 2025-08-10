package com.together.study.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.together.study.designsystem.R
import com.together.study.designsystem.component.TogedyBottomSheet
import com.together.study.designsystem.component.TogedyScheduleChip
import com.together.study.designsystem.component.TogedySearchBar
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.search.component.SearchDetailAdmissionAdd
import com.together.study.search.component.SearchDetailAdmissionSelector
import com.together.study.search.component.SearchDetailMyAdded
import com.together.study.search.component.SearchSelectorAdmissionType
import com.together.study.search.component.SearchSelectorChip
import com.together.study.search.component.SearchSelectorChipHeader
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
    val admissionType by viewModel.admissionType.collectAsStateWithLifecycle()
    val filteredList by viewModel.filteredList.collectAsStateWithLifecycle()

    val coroutineScope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var selectedUniversityId by remember { mutableStateOf<Int?>(null) }
    var isSheetVisible by remember { mutableStateOf(false) }

    val sheetViewModelStore = remember { ViewModelStore() }

    val onCloseBottomSheet: () -> Unit = {
        coroutineScope.launch {
            isSheetVisible = false
            sheetState.hide()
        }
    }

    val onOpenBottomSheet: (Int) -> Unit = { universityId ->
        selectedUniversityId = universityId
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
                modifier = Modifier
                    .padding(end = 4.dp)
                    .noRippleClickable(onBackButtonClicked)
            )
            TogedySearchBar(
                value = searchValue,
                onValueChange = { viewModel.onSearchQueryChanged(it) }
            )
        }

        SearchSelectorAdmissionType(
            selectedType = admissionType,
            onSelect = { viewModel.onAdmissionTypeChanged(it) },
            modifier = Modifier.padding(top = 8.dp)
        )

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
                        onOpenBottomSheet(data.universityId)
                    }
                )
            }
        }
    }

    if (isSheetVisible) {
        // 바텀 시트 스코프에 따라 뷰모델 생성 파괴
        CompositionLocalProvider(
            LocalViewModelStoreOwner provides object : ViewModelStoreOwner {
                override val viewModelStore: ViewModelStore
                    get() = sheetViewModelStore
            }
        ) {
            val detailViewModel: SearchDetailViewModel = viewModel()
            
            selectedUniversityId?.let { universityId ->
                detailViewModel.loadUniversityDetail(universityId)
            }
            
            val detailState by detailViewModel.searchDummy.collectAsStateWithLifecycle()

            TogedyBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    onCloseBottomSheet()
                },
                title = "대학 일정",
                showDone = false,
                onDoneClick = {
                    onCloseBottomSheet()
                },
                modifier = Modifier.height(height = 604.dp),
            ) {
                detailState?.let { data ->
                    SearchSelectorChipHeader(
                        admissionType = data.universityAdmissionType,
                        universityName = data.universityName,
                        isAdded = data.addedAdmissionMethodList.isNotEmpty(),
                        isSearchDetail = true,
                        modifier = Modifier.padding(16.dp)
                    )

                    BottomSheetContent(
                        detailState = data,
                        onDeleteAdmission = { admissionMethodId ->
                            detailViewModel.deleteAddedItem(admissionMethodId)
                        },
                        onAddAdmission = {admissionMethodId ->
                            detailViewModel.addAdmissionMethod(admissionMethodId)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun BottomSheetContent(
    detailState: SearchDetailDummy,
    onDeleteAdmission: (Int) -> Unit = {},
    onAddAdmission: (Int) -> Unit = {}
) {
    val admissionList = detailState.universityAdmissionMethodList
    var selectedIndex by remember { mutableIntStateOf(-1) } // -1은 선택되지 않은 상태
    val selectedAdmission = if (selectedIndex >= 0) admissionList.getOrNull(selectedIndex) else null

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            HorizontalDivider(
                modifier = Modifier.background(color = TogedyTheme.colors.gray100),
                thickness = 1.dp
            )

            SearchDetailMyAdded(
                addedData = detailState.addedAdmissionMethodList,
                universityAdmissionMethodList = detailState.universityAdmissionMethodList,
                onDeleteAdmission = onDeleteAdmission,
                modifier = Modifier.padding(top = 26.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 100.dp)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                selectedAdmission?.universityScheduleList?.let { scheduleList ->
                    itemsIndexed(items = scheduleList) { index, schedule ->
                        val startTime = "${schedule.startDate} ${schedule.startTime}"
                        val endTime = if (schedule.endDate != null && schedule.endTime != null) {
                            "${schedule.endDate} ${schedule.endTime}"
                        } else null

                        TogedyScheduleChip(
                            typeStatus = when (schedule.universityAdmissionStage) {
                                "원서접수" -> 1
                                "서류제출" -> 2
                                "합격발표" -> 3
                                else -> 0
                            },
                            scheduleName = detailState.universityName,
                            scheduleType = selectedAdmission.universityAdmissionMethod,
                            scheduleStartTime = startTime,
                            scheduleEndTime = endTime
                        )
                    }
                }
            }

            // 선택된 전형이 있고, 아직 추가되지 않은 경우에만 추가 버튼 표시
            selectedAdmission?.let { admission ->
                val isAlreadyAdded = detailState.addedAdmissionMethodList.contains(admission.universityAdmissionMethod)
                if (!isAlreadyAdded) {
                    SearchDetailAdmissionAdd(
                        admissionMethodId = admission.universityAdmissionMethodId,
                        onAddClick = onAddAdmission,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }
            }
        }

        SearchDetailAdmissionSelector(
            admissionList = detailState.universityAdmissionMethodList,
            selectedIndex = selectedIndex,
            onSelectionChanged = { newIndex ->
                selectedIndex = newIndex
            },
            modifier = Modifier
                .padding(top = 96.dp)
                .padding(horizontal = 16.dp)
        )
    }
}
