package com.together.study.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.search.SearchDummy
import com.together.study.util.noRippleClickable

@Composable
fun SearchSelectorChip(
    data: SearchDummy,
    modifier: Modifier = Modifier,
    onSelectorClicked: () -> Unit = {}
) {
    val isAdded = data.addedAdmissionMethodList.isNotEmpty()

    Card(
        shape = RoundedCornerShape(6.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    color = TogedyTheme.colors.white,
                    shape = RoundedCornerShape(6.dp)
                )
                .then(
                    if (isAdded) Modifier
                        .border(
                            width = 1.dp,
                            color = TogedyTheme.colors.green,
                            shape = RoundedCornerShape(6.dp)
                        ) else Modifier
                )
                .padding(horizontal = 14.dp, vertical = 10.dp)
                .noRippleClickable(onSelectorClicked)
        ) {
            SearchSelectorChipHeader(
                admissionType = data.universityAdmissionType,
                universityName = data.universityName,
                isAdded = isAdded
            )

            Spacer(modifier = Modifier.padding(vertical = 4.dp))

            SearchSelectorChipMore(
                isAdded = isAdded,
                admissionCount = data.universityAdmissionMethodCount
            )

            if (isAdded) {
                Spacer(modifier = Modifier.padding(vertical = 5.dp))
                SearchSelectorChipAdded(
                    addedList = data.addedAdmissionMethodList
                )
            }

        }
    }
}