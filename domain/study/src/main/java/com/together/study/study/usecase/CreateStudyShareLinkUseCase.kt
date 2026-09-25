package com.together.study.study.usecase

import com.together.study.study.deeplink.StudyDeepLink
import javax.inject.Inject

class CreateStudyShareLinkUseCase @Inject constructor() {
    operator fun invoke(studyId: Long, packageName: String): String =
        StudyDeepLink.shareLink(studyId, packageName)
}
