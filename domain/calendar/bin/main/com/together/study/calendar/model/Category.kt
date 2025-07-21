package com.together.study.calendar.model

data class Category(
    val categoryId: Long? = null,
    val categoryName: String? = null,
    val categoryColor: String? = null,
) {
    companion object { /* TODO: 추후 삭제 예정 */
        val mock = Category(1, "카테고리1", "CATEGORY_COLOR1")

        val mockList = listOf(
            Category(1, "카테고리1", "CATEGORY_COLOR1"),
            Category(2, "카테고리2", "CATEGORY_COLOR2"),
            Category(3, "카테고리3", "CATEGORY_COLOR3"),
            Category(4, "카테고리4", "CATEGORY_COLOR4"),
            Category(5, "카테고리5", "CATEGORY_COLOR5"),
            Category(6, "카테고리6", "CATEGORY_COLOR6"),
        )
    }
}
