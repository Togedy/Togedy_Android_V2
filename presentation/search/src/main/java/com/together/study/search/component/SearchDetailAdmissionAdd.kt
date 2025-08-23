package com.together.study.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.R.drawable.ic_add_12
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.util.noRippleClickable

@Composable
fun SearchDetailAdmissionAdd(
    modifier: Modifier = Modifier,
    admissionMethodId: Int,
    onAddClick: (Int) -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(
                color = TogedyTheme.colors.green,
                shape = RoundedCornerShape(8.dp)
            )
            .noRippleClickable { onAddClick(admissionMethodId) }
            .padding(vertical = 13.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(ic_add_12),
            contentDescription = null,
            tint = TogedyTheme.colors.white
        )
        Text(
            text = "해당 전형 추가하기",
            style = TogedyTheme.typography.body14m.copy(
                color = TogedyTheme.colors.white
            ),
            modifier = Modifier
                .padding(start = 10.dp)
        )
    }
}