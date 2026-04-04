package com.together.study.mypage.type

import com.together.study.designsystem.R.drawable.ic_bell
import com.together.study.designsystem.R.drawable.ic_edit
import com.together.study.designsystem.R.drawable.ic_help_circle

enum class SupportMenu(override val title: String, override val iconRes: Int) : MenuItemUi {
    NOTICE(title = "공지사항", iconRes = ic_bell),
    CONTACT_US(title = "문의사항", iconRes = ic_help_circle),
    LEAVE_REVIEW(title = "리뷰 남기러 가기", iconRes = ic_edit),
}
