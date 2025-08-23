package com.together.study.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.theme.TogedyTheme

@Composable
fun SearchSelectorChipHeader(
    admissionType: String,
    universityName: String,
    isAdded: Boolean,
    isSearchDetail: Boolean,
    modifier: Modifier = Modifier
) {
    val univTextColor = if (isSearchDetail) TogedyTheme.colors.black else {
        if (isAdded) TogedyTheme.colors.green else TogedyTheme.colors.gray900
    }
    val typeTextColor =
        if (admissionType == "수시") TogedyTheme.colors.gray800 else TogedyTheme.colors.white
    val typeBackColor =
        if (admissionType == "수시") TogedyTheme.colors.gray300 else TogedyTheme.colors.gray800

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = admissionType,
            style = TogedyTheme.typography.chip10sb.copy(
                color = typeTextColor
            ),
            modifier = Modifier
                .background(
                    color = typeBackColor,
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(all = 4.dp)
        )

        Text(
            text = universityName,
            style = TogedyTheme.typography.title16sb.copy(
                color = univTextColor
            ),
            modifier = Modifier.padding(start = 4.dp)
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}
