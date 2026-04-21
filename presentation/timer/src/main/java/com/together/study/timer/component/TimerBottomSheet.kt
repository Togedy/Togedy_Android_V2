package com.together.study.timer.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.together.study.common.type.planner.toPlannerSubjectColorOrDefault
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.timer.model.SubjectTimer
import com.together.study.util.asColor
import com.together.study.util.noRippleClickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TimerBottomSheet(
    scaffoldState: BottomSheetScaffoldState,
    totalTimer: String,
    selectedSubject: SubjectTimer?,
    subjects: List<SubjectTimer>,
    modifier: Modifier,
    onSubjectClick: (SubjectTimer) -> Unit,
) {
    val subjectColor = selectedSubject?.subjectColor.toPlannerSubjectColorOrDefault().asColor()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 200.dp,
        sheetContainerColor = Color.Transparent,
        containerColor = Color.Transparent,
        sheetDragHandle = { },
        sheetContent = {
            Column(
                modifier
                    .fillMaxWidth()
                    .aspectRatio(534f / 720f)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                subjectColor,
                                subjectColor.copy(alpha = 0.7f)
                            )
                        ),
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                    )
                    .padding(horizontal = 20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 12.dp, bottom = 16.dp)
                        .align(Alignment.CenterHorizontally)
                        .size(width = 42.dp, height = 2.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color.White.copy(alpha = 0.6f))
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, bottom = 20.dp, end = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "총 공부시간",
                        style = TogedyTheme.typography.body14b,
                        color = TogedyTheme.colors.white,
                    )

                    Text(
                        text = totalTimer,
                        style = TogedyTheme.typography.time46r,
                        color = TogedyTheme.colors.white,
                    )
                }

                HorizontalDivider(color = TogedyTheme.colors.white.copy(alpha = 0.2f))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                ) {
                    item {
                        Spacer(Modifier.height(10.dp))
                    }

                    items(subjects) { subject ->
                        val backgroundModifier =
                            if (subject.subjectId == selectedSubject?.subjectId)
                                Modifier.background(
                                    TogedyTheme.colors.white.copy(alpha = 0.3f),
                                    RoundedCornerShape(10.dp)
                                )
                            else Modifier

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .then(backgroundModifier)
                                .noRippleClickable { onSubjectClick(subject) }
                                .padding(horizontal = 10.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            SubjectTitle(subject = subject)

                            Text(
                                text = totalTimer,
                                style = TogedyTheme.typography.time40l,
                                color = TogedyTheme.colors.white,
                            )
                        }
                    }

                    item {
                        Spacer(Modifier.height(20.dp))
                    }
                }
            }
        }
    ) { }
}
