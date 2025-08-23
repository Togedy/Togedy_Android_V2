package com.together.study.search

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.together.study.common.state.UiState
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
import com.together.study.search.model.UnivSchedule
import com.together.study.search.type.AdmissionType
import com.together.study.util.noRippleClickable
import kotlinx.coroutines.launch

@Composable
internal fun SearchRoute(
    modifier: Modifier,
    onBackButtonClicked: () -> Unit = {},
    searchViewModel: SearchViewModel = hiltViewModel(),
) {
    val searchQuery by searchViewModel.searchQuery.collectAsStateWithLifecycle()
    val admissionType by searchViewModel.admissionType.collectAsStateWithLifecycle()
    val univScheduleState by searchViewModel.univScheduleState.collectAsStateWithLifecycle()

    SearchScreen(
        searchQuery = searchQuery,
        admissionType = admissionType,
        univScheduleState = univScheduleState,
        onBackButtonClicked = onBackButtonClicked,
        onSearchQueryChanged = searchViewModel::onSearchQueryChanged,
        onAdmissionTypeChanged = searchViewModel::onAdmissionTypeChanged,
        modifier = modifier
    )
}

@Composable
private fun SearchScreen(
    searchQuery: String,
    admissionType: AdmissionType,
    univScheduleState: UiState<List<UnivSchedule>>,
    onBackButtonClicked: () -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    onAdmissionTypeChanged: (AdmissionType) -> Unit,
    modifier: Modifier = Modifier,
) {
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
                value = searchQuery,
                onValueChange = onSearchQueryChanged
            )
        }

        SearchSelectorAdmissionType(
            selectedType = admissionType,
            onSelect = onAdmissionTypeChanged,
            modifier = Modifier.padding(top = 8.dp)
        )

        when (univScheduleState) {
            is UiState.Loading -> {
                SearchLoadingScreen()
            }
            is UiState.Failure -> {
                SearchFailureScreen(
                    errorMessage = univScheduleState.msg
                )
            }
            is UiState.Empty -> {
                SearchEmptyScreen()
            }
            is UiState.Success -> {
                SearchSuccessScreen(
                    univSchedules = univScheduleState.data
                )
            }
        }
    }
}

@Composable
private fun SearchLoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(48.dp),
            color = TogedyTheme.colors.green
        )
    }
}

@Composable
private fun SearchFailureScreen(
    errorMessage: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "데이터를 불러오는데 실패했습니다",
                color = TogedyTheme.colors.gray600
            )
            Spacer(modifier = Modifier.padding(top = 8.dp))
            Text(
                text = errorMessage,
                color = TogedyTheme.colors.gray500,
                style = TogedyTheme.typography.body12m
            )
        }
    }
}

@Composable
private fun SearchEmptyScreen() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "검색어를 입력해주세요",
            color = TogedyTheme.colors.gray600
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchSuccessScreen(
    univSchedules: List<UnivSchedule>
) {
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

    val onOpenBottomSheetInternal: (Int) -> Unit = { universityId ->
        selectedUniversityId = universityId
        coroutineScope.launch {
            isSheetVisible = true
            sheetState.show()
        }
    }

    with(univSchedules) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(size) { index ->
                val data = get(index)
                SearchSelectorChip(
                    data = data,
                    onSelectorClicked = {
                        onOpenBottomSheetInternal(data.universityId)
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
            val detailViewModel: SearchDetailViewModel = hiltViewModel()
            
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
                modifier = Modifier.height(height = 614.dp),
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
