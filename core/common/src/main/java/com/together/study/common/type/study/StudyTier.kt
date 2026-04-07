package com.together.study.common.type.study

enum class StudyTier(val title: String) {
    BRONZE_1(title = "Bronze1"),
    BRONZE_2(title = "Bronze2"),
    BRONZE_3(title = "Bronze3"),
    SLIVER_1(title = "Sliver1"),
    SLIVER_2(title = "Sliver2"),
    SLIVER_3(title = "Sliver3"),
    GOLD_1(title = "Gold1"),
    GOLD_2(title = "Gold2"),
    GOLD_3(title = "Gold3"),
    MASTER(title = "Master"),
    LEGEND(title = "Legend");

    companion object {
        fun get(studyTier: String): StudyTier {
            return try {
                StudyTier.valueOf(studyTier.uppercase())
            } catch (e: Exception) {
                BRONZE_1
            }
        }
    }
}