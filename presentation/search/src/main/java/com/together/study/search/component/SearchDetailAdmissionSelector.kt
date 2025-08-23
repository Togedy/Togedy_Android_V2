package com.together.study.search.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.R.drawable.ic_arrow_down_24
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.search.UniversityAdmissionMethod
import com.together.study.util.noRippleClickable

@Composable
fun SearchDetailAdmissionSelector(
    admissionList: List<UniversityAdmissionMethod>,
    selectedIndex: Int,
    onSelectionChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }
    val selectorText = if (selectedIndex >= 0) {
        admissionList.getOrNull(selectedIndex)?.universityAdmissionMethod ?: "전형을 선택하세요"
    } else {
        "전형을 선택하세요"
    }
    val selectorStyle = TogedyTheme.typography.body14m.copy(
        color = if (selectedIndex >= 0) TogedyTheme.colors.black else TogedyTheme.colors.gray400
    )
    val rotation by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        label = "arrowRotation"
    )

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "전체 수시 전형",
            style = TogedyTheme.typography.chip14b.copy(
                color = TogedyTheme.colors.black
            ),
        )

        LazyColumn(
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth()
                .background(
                    color = TogedyTheme.colors.white,
                    shape = RoundedCornerShape(8.dp)
                )
                .border(
                    width = 2.dp,
                    color = TogedyTheme.colors.gray300,
                    shape = RoundedCornerShape(8.dp)
                ),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            // index == 0 인 경우, "전형을 선택하세요" 또는 선택된 전형
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp, horizontal = 12.dp)
                        .noRippleClickable {
                            isExpanded = !isExpanded
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectorText,
                        style = selectorStyle,
                        modifier = Modifier.weight(1f)
                    )

                    Icon(
                        painter = painterResource(ic_arrow_down_24),
                        contentDescription = null,
                        tint = TogedyTheme.colors.gray400,
                        modifier = Modifier.rotate(rotation)
                    )
                }
            }

            // 확장된 경우 admissionList 표시
            if (isExpanded) {
                itemsIndexed(admissionList) { index, admission ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = if (index == selectedIndex) TogedyTheme.colors.gray50 else TogedyTheme.colors.white
                            )
                            .noRippleClickable {
                                onSelectionChanged(index)
                                isExpanded = false
                            }
                    ) {
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = TogedyTheme.colors.gray200,
                        )

                        Text(
                            text = admission.universityAdmissionMethod,
                            style = TogedyTheme.typography.body14m.copy(
                                color = if (index == selectedIndex) TogedyTheme.colors.black else TogedyTheme.colors.gray600
                            ),
                            modifier = Modifier
                                .padding(vertical = 12.dp, horizontal = 12.dp)
                        )
                    }
                }
            }
        }
    }
}