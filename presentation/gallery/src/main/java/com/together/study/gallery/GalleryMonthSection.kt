package com.together.study.gallery

import java.time.YearMonth

data class GalleryMonthSection(
    val yearMonth: YearMonth,
    val images: List<GalleryImage>,
)
