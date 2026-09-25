package com.together.study.study.deeplink

import java.net.URI
import java.net.URLEncoder

object StudyDeepLink {
    const val SCHEME = "togedy"
    const val HOST = "study"
    const val PLAY_STORE_PACKAGE = "com.together.study"

    fun shareLink(studyId: Long, packageName: String): String {
        val fallback = URLEncoder.encode(playStoreUrl(), Charsets.UTF_8.name())
        return "intent://$HOST/$studyId#Intent;" +
            "scheme=$SCHEME;" +
            "package=$packageName;" +
            "S.browser_fallback_url=$fallback;" +
            "end"
    }

    fun parseStudyId(uriString: String?): Long? {
        if (uriString.isNullOrBlank()) return null
        val uri = runCatching { URI(uriString) }.getOrNull() ?: return null
        if (uri.scheme != SCHEME || uri.host != HOST) return null
        return uri.path?.trim('/')?.toLongOrNull()
    }

    private fun playStoreUrl(): String =
        "https://play.google.com/store/apps/details?id=$PLAY_STORE_PACKAGE"
}
