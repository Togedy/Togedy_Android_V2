package com.together.study.study.usecase

import com.together.study.study.deeplink.StudyDeepLink
import javax.inject.Inject

class ParseStudyDeepLinkUseCase @Inject constructor() {
    operator fun invoke(uriString: String?): Long? = StudyDeepLink.parseStudyId(uriString)
}
