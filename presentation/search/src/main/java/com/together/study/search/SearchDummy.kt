package com.together.study.search

data class SearchDummy(
    val universityId: Int,
    val universityName: String,
    val universityAdmissionType: String,
    val universityAdmissionMethodCount: Int,
    val addedAdmissionMethodList: List<String>
)

// 더미 데이터 생성 함수
fun dummySearchList(): List<SearchDummy> {
    return listOf(
        SearchDummy(
            universityId = 1,
            universityName = "건국대학교(서울캠퍼스)",
            universityAdmissionType = "수시",
            universityAdmissionMethodCount = 4,
            addedAdmissionMethodList = listOf("학생부종합전형", "학생부교과전형")
        ),
        SearchDummy(
            universityId = 2,
            universityName = "건국대학교(서울캠퍼스)",
            universityAdmissionType = "수시",
            universityAdmissionMethodCount = 4,
            addedAdmissionMethodList = emptyList()
        ),
        SearchDummy(
            universityId = 3,
            universityName = "서울대학교(서울캠퍼스)",
            universityAdmissionType = "정시",
            universityAdmissionMethodCount = 3,
            addedAdmissionMethodList = listOf("정시 일반 전형")
        ),
        SearchDummy(
            universityId = 4,
            universityName = "고려대학교(서울캠퍼스)",
            universityAdmissionType = "정시",
            universityAdmissionMethodCount = 2,
            addedAdmissionMethodList = emptyList()
        ),
        // Additional dummy data
        SearchDummy(
            universityId = 5,
            universityName = "연세대학교(서울캠퍼스)",
            universityAdmissionType = "정시",
            universityAdmissionMethodCount = 3,
            addedAdmissionMethodList = listOf("정시 일반 전형")
        ),
        SearchDummy(
            universityId = 6,
            universityName = "한양대학교(서울캠퍼스)",
            universityAdmissionType = "수시",
            universityAdmissionMethodCount = 2,
            addedAdmissionMethodList = listOf("정보콘텐츠학과 전형", "글로벌융합공학부 전형")
        ),
        SearchDummy(
            universityId = 7,
            universityName = "성균관대학교(서울캠퍼스)",
            universityAdmissionType = "수시",
            universityAdmissionMethodCount = 4,
            addedAdmissionMethodList = listOf("대학입학전형", "일반전형")
        ),
        SearchDummy(
            universityId = 8,
            universityName = "중앙대학교(서울캠퍼스)",
            universityAdmissionType = "정시",
            universityAdmissionMethodCount = 3,
            addedAdmissionMethodList = listOf("정시 일반 전형")
        ),
        SearchDummy(
            universityId = 9,
            universityName = "경희대학교(서울캠퍼스)",
            universityAdmissionType = "정시",
            universityAdmissionMethodCount = 2,
            addedAdmissionMethodList = listOf("정시 일반 전형")
        ),
        SearchDummy(
            universityId = 10,
            universityName = "한국외국어대학교(서울캠퍼스)",
            universityAdmissionType = "정시",
            universityAdmissionMethodCount = 2,
            addedAdmissionMethodList = emptyList()
        )
    )
}
