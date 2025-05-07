package com.together.study.buildlogic.primitive

import com.together.study.buildlogic.dsl.androidTestImplementation
import com.together.study.buildlogic.dsl.debugImplementation
import com.together.study.buildlogic.dsl.implementation
import com.together.study.buildlogic.dsl.implementationPlatform
import com.together.study.buildlogic.dsl.library
import com.together.study.buildlogic.dsl.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

class ComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(plugins) {
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            extensions.getByType<ComposeCompilerGradlePluginExtension>().apply {
                val isRelease = System.getenv("BUILD_TYPE") == "release"

                enableStrongSkippingMode.set(true)
                includeSourceInformation.set(!isRelease)
                includeTraceMarkers.set(!isRelease)
            }

            dependencies {
                implementationPlatform(libs.library("compose-bom"))
                implementation(libs.library("compose-ui"))
                implementation(libs.library("compose-ui-graphics"))
                implementation(libs.library("compose-ui-tooling-preview"))
                implementation(libs.library("compose-material3"))
                implementation(libs.library("androidx-lifecycle-viewmodel-compose"))
                implementation(libs.library("androidx-lifecycle-runtime-compose"))
                androidTestImplementation(platform(libs.library("compose-bom")))
                androidTestImplementation(libs.library("compose-ui-test-junit4"))
                debugImplementation(libs.library("compose-ui-tooling"))
                debugImplementation(libs.library("compose-ui-test-manifest"))
            }
        }
    }
}
