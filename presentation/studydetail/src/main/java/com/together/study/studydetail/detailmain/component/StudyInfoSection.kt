package com.together.study.studydetail.detailmain.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.together.study.designsystem.component.textchip.TogedyBasicTextChip
import com.together.study.designsystem.theme.TogedyTheme

@Composable
internal fun StudyInfoSection(
    studyTag: String,
    studyName: String,
    studyDescription: String?,
    studyTier: String,
    studyMemberCount: Int,
    studyMemberLimit: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(TogedyTheme.colors.white)
            .padding(16.dp),
    ) {
        TogedyBasicTextChip(
            text = studyTag,
            textStyle = TogedyTheme.typography.body10m,
            textColor = TogedyTheme.colors.gray700,
            horizontalPadding = 6.dp,
            backgroundColor = TogedyTheme.colors.gray200,
        )

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = studyName,
                style = TogedyTheme.typography.title18b,
                color = TogedyTheme.colors.black,
            )

            Spacer(Modifier.width(4.dp))

            if (studyTier.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(
                            TogedyTheme.colors.gray200,
                            RoundedCornerShape(10.dp)
                        )
                )
            }

            Spacer(Modifier.width(8.dp))

            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = TogedyTheme.colors.green)) {
                        append(studyMemberCount.toString())
                    }
                    withStyle(SpanStyle(color = TogedyTheme.colors.gray500)) {
                        append("/$studyMemberLimit")
                    }
                },
                style = TogedyTheme.typography.body14m,
            )
        }

        Spacer(Modifier.height(12.dp))

        if (!studyDescription.isNullOrEmpty()) {
            TextWithMoreButton(
                text = studyDescription,
            )
        }
    }
}
