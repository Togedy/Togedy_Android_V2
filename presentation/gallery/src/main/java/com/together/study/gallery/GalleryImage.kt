package com.together.study.gallery

import android.net.Uri
import java.time.Instant
import java.time.YearMonth
import java.time.ZoneId

data class GalleryImage(
    val id: Long,
    val uri: Uri,
    val dateMillis: Long,
)

fun List<GalleryImage>.toMonthSections(): List<GalleryMonthSection> =
    groupByMonth().map { (month, images) ->
        GalleryMonthSection(month, images)
    }

fun List<GalleryImage>.groupByMonth(): Map<YearMonth, List<GalleryImage>> =
    groupBy { image ->
        YearMonth.from(
            Instant.ofEpochMilli(image.dateMillis)
                .atZone(ZoneId.systemDefault())
        )
    }.toSortedMap(compareByDescending { it })
