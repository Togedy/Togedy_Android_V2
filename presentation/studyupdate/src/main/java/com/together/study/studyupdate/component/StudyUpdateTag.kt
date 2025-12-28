package com.together.study.studyupdate.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.together.study.studyupdate.StudyUpdateTextItem

@Composable
internal fun StudyUpdateTag(
    selectedCategory: String?,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    StudyUpdateTextItem(
        modifier = modifier.padding(top = 32.dp),
        inputTitle = "스터디 유형",
        inputEssential = true,
        inputTitleSub = "택1"
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StudyUpdateTagTextChip(
                    text = "내신/학교생활",
                    selected = selectedCategory == "내신/학교생활",
                    onClick = { onSelect("내신/학교생활") },
                    modifier = Modifier.weight(1f),
                )

                StudyUpdateTagTextChip(
                    text = "대학입시/편입",
                    selected = selectedCategory == "대학입시/편입",
                    onClick = { onSelect("대학입시/편입") },
                    modifier = Modifier.weight(1f),
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StudyUpdateTagTextChip(
                    text = "취업/자격증",
                    selected = selectedCategory == "취업/자격증",
                    onClick = { onSelect("취업/자격증") },
                    modifier = Modifier.weight(1f)
                )
                StudyUpdateTagTextChip(
                    text = "자유스터디",
                    selected = selectedCategory == "자유스터디",
                    onClick = { onSelect("자유스터디") },
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

