package com.together.study.buildlogic.dsl

import org.gradle.api.Project

fun Project.configureAndroidLibrary() {
    androidExtension.apply {
        compileSdk = libs.version("compileSdk").toInt()
        buildToolsVersion = libs.version("buildTools")

        defaultConfig.minSdk = libs.version("minSdk").toInt()
    }
}
