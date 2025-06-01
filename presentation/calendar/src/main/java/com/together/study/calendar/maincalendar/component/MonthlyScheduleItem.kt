package com.together.study.calendar.maincalendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.calendar.model.Category
import com.together.study.calendar.model.Schedule
import com.together.study.calendar.type.toBackgroundColorOrDefault
import com.together.study.calendar.type.toCategoryColorOrDefault
import com.together.study.designsystem.theme.TogedyTheme
import com.together.study.presentation.calendar.R.string.university_schedule_name
import java.time.LocalDate

@Composable
fun MonthlyScheduleItem(
    schedule: Schedule,
    modifier: Modifier = Modifier,
) {
    with(schedule) {
        val isUserSchedule = scheduleType == "user"

        if (isUserSchedule) {
            UserSchedule(
                scheduleName = scheduleName,
                categoryColor = category?.categoryColor.toString(),
                modifier = modifier,
            )
        } else {
            UnivSchedule(
                scheduleName = scheduleName,
                universityAdmissionType = universityAdmissionType,
                universityAdmissionStage = universityAdmissionStage,
                modifier = modifier,
            )
        }
    }
}

@Composable
private fun UserSchedule(
    scheduleName: String,
    categoryColor: String,
    modifier: Modifier = Modifier,
) {
    val categoryColor = categoryColor.toCategoryColorOrDefault()

    Row(
        modifier = modifier
            .background(
                color = categoryColor.toBackgroundColorOrDefault(),
                shape = RoundedCornerShape(2.dp),
            ),
    ) {
        Box(
            modifier = Modifier
                .width(2.dp)
                .height(12.dp)
                .background(
                    color = categoryColor,
                    shape = RoundedCornerShape(2.dp),
                ),
        )

        Spacer(Modifier.width(2.dp))

        Text(
            text = scheduleName,
            style = TogedyTheme.typography.body10m.copy(categoryColor),
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun UnivSchedule(
    scheduleName: String,
    universityAdmissionType: String,
    universityAdmissionStage: String,
    modifier: Modifier = Modifier,
) {
    val universityScheduleColor = TogedyTheme.colors.green

    Row(
        modifier = modifier
            .background(
                color = universityScheduleColor.toBackgroundColorOrDefault(),
                shape = RoundedCornerShape(2.dp),
            ),
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(
                    color = TogedyTheme.colors.green,
                    shape = RoundedCornerShape(2.dp),
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = universityAdmissionStage.take(1),
                style = TogedyTheme.typography.body10m.copy(TogedyTheme.colors.white),
            )
        }

        Spacer(Modifier.width(2.dp))

        Text(
            text = stringResource(
                university_schedule_name,
                scheduleName,
                universityAdmissionStage,
                universityAdmissionType
            ),
            style = TogedyTheme.typography.body10m.copy(universityScheduleColor),
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UserSchedulePreview(modifier: Modifier = Modifier) {
    TogedyTheme {
        MonthlyScheduleItem(
            schedule = Schedule(
                scheduleId = null,
                scheduleType = "user",
                scheduleName = "국어학원",
                startDate = LocalDate.now().toString(),
                endDate = null,
                universityAdmissionType = "",
                universityAdmissionStage = "",
                category = Category(
                    categoryId = null,
                    categoryName = "국어",
                    categoryColor = "CATEGORY_COLOR1",
                )
            ),
            modifier = modifier,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UniversitySchedulePreview(modifier: Modifier = Modifier) {
    TogedyTheme {
        MonthlyScheduleItem(
            schedule = Schedule(
                scheduleId = null,
                scheduleType = "university",
                scheduleName = "건국대학교",
                startDate = LocalDate.now().toString(),
                endDate = null,
                universityAdmissionType = "수시",
                universityAdmissionStage = "원서접수",
                category = Category(
                    categoryId = null,
                    categoryName = "국어",
                    categoryColor = "CATEGORY_COLOR1",
                )
            ),
            modifier = modifier,
        )
    }
}
