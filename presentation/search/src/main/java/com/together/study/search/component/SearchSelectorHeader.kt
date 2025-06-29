package com.together.study.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.together.study.util.noRippleClickable

@Composable
fun SearchSelectorHeader(
    admissionType: String,
    universityName: String,
    isAdded: Boolean,
    onClickScheduleAdd: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = admissionType,
            style = TogedyTheme.typography.chip10sb.copy(
                color = if (admissionType == "수시")
                    TogedyTheme.colors.gray700
                else
                    TogedyTheme.colors.white
            ),
            modifier = Modifier
                .background(
                    color = if (admissionType == "수시")
                        TogedyTheme.colors.gray100
                    else
                        TogedyTheme.colors.black,
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(all = 4.dp)
        )

        Text(
            text = universityName,
            style = TogedyTheme.typography.title16sb.copy(
                color = TogedyTheme.colors.green
            ),
            modifier = Modifier.padding(start = 4.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = if(isAdded) "캘린더 삭제" else "캘린더 추가",
            style = TogedyTheme.typography.body12m.copy(
                color = TogedyTheme.colors.green
            ),
            modifier = Modifier
                .background(
                    color = if (isAdded)
                        TogedyTheme.colors.white
                    else
                        TogedyTheme.colors.greenBg,
                    shape = RoundedCornerShape(4.dp)
                )
                .then(
                    if (isAdded) Modifier.border(
                        width = 1.dp,
                        color = TogedyTheme.colors.green,
                        shape = RoundedCornerShape(4.dp)
                    ) else Modifier
                )
                .noRippleClickable(onClickScheduleAdd)
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}
