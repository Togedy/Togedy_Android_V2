package com.together.study.planner.util

import androidx.compose.ui.graphics.Color
import com.together.study.planner.type.AbsentRange
import com.together.study.planner.type.StreakRange
import com.together.study.planner.type.StudyStatus
import com.together.study.planner.type.StudyStatusUiModel
import com.together.study.designsystem.R.drawable.img_streak_1day
import com.together.study.designsystem.R.drawable.img_streak_7day
import com.together.study.designsystem.R.drawable.img_streak_59day
import com.together.study.designsystem.R.drawable.img_streak_99day
import com.together.study.designsystem.R.drawable.img_streak_200day
import com.together.study.designsystem.R.drawable.img_streak_200dayover
import com.together.study.designsystem.R.drawable.img_no_1day
import com.together.study.designsystem.R.drawable.img_no_3day
import com.together.study.designsystem.R.drawable.img_no_7day
import com.together.study.designsystem.R.drawable.img_no_30day
import com.together.study.designsystem.R.drawable.img_no_50day
import com.together.study.designsystem.R.drawable.img_no_99day
import com.together.study.designsystem.R.drawable.img_no_199day
import com.together.study.designsystem.R.drawable.img_no_200day
import com.together.study.designsystem.R.drawable.img_no_200dayover

fun StudyStatus.toUiModel(): StudyStatusUiModel {
    return when (this) {
        is StudyStatus.Streak -> {
            when (range) {
                StreakRange.DAY_1 -> StudyStatusUiModel(
                    text = "기념적인 날이네요!",
                    imageRes = img_streak_1day,
                )

                StreakRange.DAY_2_7 -> StudyStatusUiModel(
                    text = "기념적인 날이네요!",
                    imageRes = img_streak_7day,
                )

                StreakRange.DAY_8_59 -> StudyStatusUiModel(
                    text = "이 기세를 유지해요!!!!",
                    imageRes = img_streak_59day,
                )

                StreakRange.DAY_60_99 -> StudyStatusUiModel(
                    text = "이 기세를 유지해요!!!!",
                    imageRes = img_streak_99day,
                )

                StreakRange.DAY_100_199 -> StudyStatusUiModel(
                    text = "이 기세를 유지해요!!!!",
                    imageRes = img_streak_200day,
                )

                StreakRange.DAY_200_PLUS -> StudyStatusUiModel(
                    text = "이 기세를 유지해요!!!!",
                    imageRes = img_streak_200dayover,
                )
            }
        }

        is StudyStatus.Absent -> {
            when (range) {
                AbsentRange.DAY_1 -> StudyStatusUiModel(
                    text = "어제 놓쳤네요!",
                    imageRes = img_no_1day,
                    backgroundColor = Color.Yellow
                )

                AbsentRange.DAY_2_3 -> StudyStatusUiModel(
                    text = "다시 시작해볼까요?",
                    imageRes = img_no_3day,
                    backgroundColor = Color.Red
                )

                AbsentRange.DAY_4_7 -> StudyStatusUiModel(
                    text = "다시 시작해볼까요?",
                    imageRes = img_no_7day,
                    backgroundColor = Color.Red
                )

                AbsentRange.DAY_8_30 -> StudyStatusUiModel(
                    text = "다시 시작해볼까요?",
                    imageRes = img_no_30day,
                    backgroundColor = Color.Red
                )

                AbsentRange.DAY_31_50 -> StudyStatusUiModel(
                    text = "다시 시작해볼까요?",
                    imageRes = img_no_50day,
                    backgroundColor = Color.Red
                )

                AbsentRange.DAY_51_99 -> StudyStatusUiModel(
                    text = "다시 시작해볼까요?",
                    imageRes = img_no_99day,
                    backgroundColor = Color.Red
                )

                AbsentRange.DAY_100_199 -> StudyStatusUiModel(
                    text = "다시 시작해볼까요?",
                    imageRes = img_no_199day,
                    backgroundColor = Color.Red
                )

                AbsentRange.DAY_200_PLUS -> StudyStatusUiModel(
                    text = "다시 시작해볼까요?",
                    imageRes = img_no_200dayover,
                    backgroundColor = Color.Red
                )
            }
        }
    }
}
