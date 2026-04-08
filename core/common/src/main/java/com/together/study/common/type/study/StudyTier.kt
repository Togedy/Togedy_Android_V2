package com.together.study.common.type.study

enum class StudyTier(val title: String) {
    BRONZE_1(title = "Bronze1"),
    BRONZE_2(title = "Bronze2"),
    BRONZE_3(title = "Bronze3"),
    SILVER_1(title = "Silver1"),
    SILVER_2(title = "Silver2"),
    SILVER_3(title = "Silver3"),
    GOLD_1(title = "Gold1"),
    GOLD_2(title = "Gold2"),
    GOLD_3(title = "Gold3"),
    MASTER(title = "Master"),
    LEGEND(title = "Legend");

    companion object {
        fun get(studyTier: String): StudyTier {
            val normalized = studyTier.replace("_", "").lowercase()

            return StudyTier.entries.find {
                it.name.replace("_", "").lowercase() == normalized ||
                        it.title.replace(" ", "").lowercase() == normalized
            } ?: BRONZE_1
        }
    }
}