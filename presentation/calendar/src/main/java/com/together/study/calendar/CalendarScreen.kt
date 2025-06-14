package com.together.study.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.component.TogedySearchBar

@Composable
fun CalendarScreen(
    onSearchClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 50.dp)
    ) {
        TogedySearchBar(
            isShowSearch = true,
            onSearchClicked = onSearchClicked
        )
    }
}

@Preview
@Composable
fun CalendarScreenPreview(){
    CalendarScreen(
        onSearchClicked = {}
    )
}