package com.together.study.gallery.usecase

import com.together.study.gallery.model.GalleryImage
import com.together.study.gallery.model.GalleryMonthSection
import java.time.Instant
import java.time.YearMonth
import java.time.ZoneId
import javax.inject.Inject

class GroupImagesByMonthUseCase @Inject constructor() {
    operator fun invoke(images: List<GalleryImage>)
            : List<GalleryMonthSection> {

        return images
            .groupBy {
                YearMonth.from(
                    Instant.ofEpochMilli(it.dateMillis)
                        .atZone(ZoneId.systemDefault())
                )
            }
            .toSortedMap(compareByDescending { it })
            .map { (month, list) ->
                GalleryMonthSection(month, list)
            }
    }
}
