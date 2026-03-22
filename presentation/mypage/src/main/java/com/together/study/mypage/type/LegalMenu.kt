package com.together.study.mypage.type

import com.together.study.designsystem.R.drawable.ic_bell
import com.together.study.designsystem.R.drawable.ic_help_circle

enum class LegalMenu(override val title: String, override val iconRes: Int) : MenuItemUi {
    TERMS_OF_SERVICE(title = "서비스 이용약관", iconRes = ic_bell),
    PRIVACY_POLICY(title = "개인정보처리방침", iconRes = ic_help_circle),
}
