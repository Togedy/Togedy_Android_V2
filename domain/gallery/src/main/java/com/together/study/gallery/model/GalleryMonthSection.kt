package com.together.study.gallery.model

import java.time.YearMonth

data class GalleryMonthSection(
    val yearMonth: YearMonth,
    val images: List<GalleryImage>,
)
