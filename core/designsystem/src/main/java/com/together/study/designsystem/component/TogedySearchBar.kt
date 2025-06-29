package com.together.study.designsystem.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.R
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable
import kotlinx.coroutines.launch

/**
 * 검색 이미지가 있는 경우는 화면이동, 없는 경우는 서치가 되는 검색바 입니다.
 * @param modifier Modifier 정의
 * @param isShowSearch search 아이콘 유뮤
 * @param value textfield text 정의
 * @param onValueChange textfield text 변경 함수
 * @param onSearchClicked search 아이콘 있는 경우 클릭 함수
 */

@Composable
fun TogedySearchBar(
    isShowSearch: Boolean = false,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    onSearchClicked: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val isTextFieldMode = !isShowSearch
    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = if (isTextFieldMode && isFocused) TogedyTheme.colors.gray700
                else TogedyTheme.colors.gray400,
                shape = RoundedCornerShape(4.dp)
            )
            .background(
                color = if (isTextFieldMode) TogedyTheme.colors.white else TogedyTheme.colors.gray200,
            )
            .padding(horizontal = 7.dp)
            .then(
                if (isShowSearch) {
                    Modifier.noRippleClickable(onSearchClicked)
                } else Modifier
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if (isShowSearch) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search_24),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 3.dp)
                )
            }

            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                enabled = isTextFieldMode,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 5.dp)
                    .padding(vertical = 13.dp)
                    .onFocusChanged {
                        isFocused = it.isFocused
                    },
                textStyle = TogedyTheme.typography.body12m.copy(
                    color = TogedyTheme.colors.gray700
                ),
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Text(
                            text = "원하는 대학을 검색하세요",
                            style = TogedyTheme.typography.body12m.copy(
                                color = Color.LightGray
                            )
                        )
                    }
                    innerTextField()
                }
            )

            if (isTextFieldMode && value.isNotEmpty()) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search_cancel_16),
                    contentDescription = null,
                    tint = Color.Companion.Gray,
                    modifier = Modifier
                        .padding(start = 13.dp, end = 6.dp)
                        .size(20.dp)
                        .noRippleClickable {
                            onValueChange("")
                        }
                )
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TogedySearchBarPreview() {
    var text by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isSheetVisible by remember { mutableStateOf(false) }

    val onCloseBottomSheet: () -> Unit = {
        scope.launch {
            isSheetVisible = false
            sheetState.hide()
        }
    }

    val onOpenBottomSheet: () -> Unit = {
        scope.launch {
            isSheetVisible = true
            sheetState.show()
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Companion.White)
            .padding(horizontal = 30.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TogedySearchBar(
            isShowSearch = true,
            onSearchClicked = { Log.d("화면이동", "화면이동") }
        )

        Spacer(modifier = Modifier.height(30.dp))

        TogedySearchBar(
            value = text,
            isShowSearch = false,
            onValueChange = { text = it },
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "바텀 시트 클릭 출력",
            modifier = Modifier.Companion.noRippleClickable {
                onOpenBottomSheet()
            }
        )

        Spacer(modifier = Modifier.height(30.dp))

        TogedyScheduleChip(
            typeStatus = 1,
            scheduleName = "건국대학교(서울캠) 원서접수 [정시]",
            scheduleType = "원서접수",
            scheduleEndTime = "05.04 23:00"
        )

        Spacer(modifier = Modifier.height(30.dp))

        TogedyScheduleChip(
            typeStatus = 3,
            scheduleName = "건국대학교(서울캠) 원서접수 [수시]",
            scheduleType = "원서접수",
            scheduleStartTime = "05.04 22:00",
            scheduleEndTime = "05.04 23:00"
        )
    }

    if (isSheetVisible) {
        TogedyBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                onCloseBottomSheet()
            },
            title = "옵션 선택",
            showDone = true,
            onDoneClick = {
                onCloseBottomSheet()
            }
        ) {
            Column {
                repeat(5) {
                    Text("아이템 ${it + 1}", modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}