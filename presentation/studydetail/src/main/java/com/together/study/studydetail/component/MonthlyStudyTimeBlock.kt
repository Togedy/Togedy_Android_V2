package com.together.study.studydetail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.together.study.study.model.StudyMemberTimeBlocks
import com.together.study.studydetail.detailmain.component.StudyMonthlyColorBlock
import java.time.LocalDate

@Composable
internal fun MonthlyStudyTimeBlock(
    monthlyStudyTimeList: List<StudyMemberTimeBlocks.MonthlyStudyTime>,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        reverseLayout = true,
    ) {
        item { Spacer(Modifier.width(6.dp)) }

        if (monthlyStudyTimeList.isEmpty()) {
            items(createEmptyMonthsData()) { (year, month, days) ->
                StudyMonthlyColorBlock(
                    year = year,
                    month = month,
                    studyTimeList = days,
                )
            }
        } else {
            items(monthlyStudyTimeList.asReversed()) { item ->
                StudyMonthlyColorBlock(
                    year = item.year,
                    month = item.month,
                    studyTimeList = item.studyTimeList,
                )
            }
        }

        item { Spacer(Modifier.width(6.dp)) }
    }
}

/**
 * 빈 리스트일 때 사용할 2개월치 데이터 생성
 */
private fun createEmptyMonthsData(): List<Triple<Int, Int, List<Int>>> {
    val now = LocalDate.now()

    return listOf(
        now.withDayOfMonth(1),
        now.withDayOfMonth(1).minusMonths(1)
    ).map { date ->
        val days = List(date.lengthOfMonth()) { 1 }
        Triple(date.year, date.monthValue, days)
    }
}

@Preview
@Composable
private fun MonthlyStudyTimeBlockPreview() {
    MonthlyStudyTimeBlock(
        monthlyStudyTimeList = emptyList(),
    )
}
