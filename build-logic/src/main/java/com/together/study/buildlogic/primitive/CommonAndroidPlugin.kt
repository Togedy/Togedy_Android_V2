package com.together.study.buildlogic.primitive

import com.together.study.buildlogic.dsl.androidExtension
import com.together.study.buildlogic.dsl.implementation
import com.together.study.buildlogic.dsl.library
import com.together.study.buildlogic.dsl.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class CommonAndroidPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        androidExtension.buildFeatures.buildConfig = true
        androidExtension.defaultConfig.testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"

        dependencies {
            implementation(libs.library("androidx-core-ktx"))
        }
    }
}
