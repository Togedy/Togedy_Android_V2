package com.together.study.search.type

enum class AdmissionType(
    val index: Int,
    val admissionName: String
) {
    ALL(0, "전체"),
    EARLY(1, "수시"),
    REGULAR(2, "정시");

    companion object {
        fun fromIndex(index: Int): AdmissionType =
            AdmissionType.entries.find { it.index == index } ?: ALL

        fun fromAdmissionName(name: String): AdmissionType =
            AdmissionType.entries.find { it.admissionName == name } ?: ALL

        fun toIndex(type: AdmissionType): Int = type.index

        fun toDisplayName(type: AdmissionType): String = type.admissionName

        fun admissionNameFromIndex(index: Int): String =
            fromIndex(index).admissionName

        fun indexFromAdmissionName(name: String): Int =
            fromAdmissionName(name).index
    }
} 