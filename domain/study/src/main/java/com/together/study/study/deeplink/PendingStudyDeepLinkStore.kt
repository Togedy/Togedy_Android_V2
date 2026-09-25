package com.together.study.study.deeplink

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PendingStudyDeepLinkStore @Inject constructor() {
    @Volatile
    private var studyId: Long? = null

    fun offer(id: Long) {
        studyId = id
    }

    fun consume(): Long? = studyId.also { studyId = null }
}
