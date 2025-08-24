package com.together.study.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.R.drawable.ic_delete_x_16
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.search.model.UnivDetailAdmissionMethod
import com.together.study.util.noRippleClickable

@Composable
fun SearchDetailMyAdded(
    modifier: Modifier = Modifier,
    addedData: List<String>,
    universityAdmissionMethodList: List<UnivDetailAdmissionMethod>,
    onDeleteAdmission: (Int) -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "내가 추가한 전형",
            style = TogedyTheme.typography.chip14b.copy(
                color = TogedyTheme.colors.black
            ),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(addedData) { index, item ->
                // 해당 아이템의 admissionMethodId
                val admissionMethod = universityAdmissionMethodList.find {
                    it.universityAdmissionMethod == item
                }

                Row(
                    modifier = Modifier
                        .background(
                            color = TogedyTheme.colors.gray100,
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(vertical = 4.dp)
                        .padding(start = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item,
                        style = TogedyTheme.typography.body14m.copy(
                            color = TogedyTheme.colors.gray600
                        )
                    )

                    Icon(
                        imageVector = ImageVector.vectorResource(ic_delete_x_16),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 4.dp, end = 8.dp)
                            .noRippleClickable {
                                admissionMethod?.let { method ->
                                    onDeleteAdmission(method.universityAdmissionMethodId)
                                }
                            },
                        tint = TogedyTheme.colors.gray500
                    )
                }
            }
        }
    }
}
