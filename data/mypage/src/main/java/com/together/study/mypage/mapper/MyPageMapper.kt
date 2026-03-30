package com.together.study.mypage.mapper

import com.together.study.mypage.dto.NoticeResponse
import com.together.study.mypage.model.Notice

fun NoticeResponse.toDomain(): Notice {
    return Notice(
        noticeTitle = this.noticeTitle,
        noticeContent = this.noticeContent,
        publishedAt = this.publishedAt,
    )
}

fun NoticeResponse.toDomainList(): Notice {
    return Notice(
        noticeId = this.noticeId,
        noticeTitle = this.noticeTitle,
        publishedAt = this.publishedAt,
    )
}
