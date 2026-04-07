package com.together.study.designsystem.mapper

import com.together.study.common.type.study.StudyTier
import com.together.study.designsystem.R

object StudyTierUiMapper {

    fun map(tier: StudyTier): StudyTierUiModel {
        return when (tier) {
            StudyTier.BRONZE_1 -> StudyTierUiModel(
                iconRes = R.drawable.tier_bronze_1,
                label = "브론즈 1",
            )

            StudyTier.BRONZE_2 -> StudyTierUiModel(
                iconRes = R.drawable.tier_bronze_2,
                label = "브론즈 2"
            )

            StudyTier.BRONZE_3 -> StudyTierUiModel(
                iconRes = R.drawable.tier_bronze_3,
                label = "브론즈 3",
            )

            StudyTier.SLIVER_1 -> StudyTierUiModel(
                iconRes = R.drawable.tier_sliver_1,
                label = "실버 1",
            )

            StudyTier.SLIVER_2 -> StudyTierUiModel(
                iconRes = R.drawable.tier_sliver_2,
                label = "실버 2",
            )

            StudyTier.SLIVER_3 -> StudyTierUiModel(
                iconRes = R.drawable.tier_sliver_3,
                label = "실버 3",
            )

            StudyTier.GOLD_1 -> StudyTierUiModel(
                iconRes = R.drawable.tier_gold_1,
                label = "골드 1",
            )

            StudyTier.GOLD_2 -> StudyTierUiModel(
                iconRes = R.drawable.tier_gold_2,
                label = "골드 2",
            )

            StudyTier.GOLD_3 -> StudyTierUiModel(
                iconRes = R.drawable.tier_gold_3,
                label = "골드 3",
            )

            StudyTier.MASTER -> StudyTierUiModel(
                iconRes = R.drawable.tier_master,
                label = "마스터",
            )

            StudyTier.LEGEND -> StudyTierUiModel(
                iconRes = R.drawable.tier_legend,
                label = "레전드",
            )
        }
    }

    fun StudyTier.toUiModel(): StudyTierUiModel {
        return map(this)
    }
}
