package com.together.study.buildlogic.primitive

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import com.together.study.buildlogic.dsl.implementation
import com.together.study.buildlogic.dsl.implementationPlatform
import com.together.study.buildlogic.dsl.library
import com.together.study.buildlogic.dsl.libs

class OkHttpPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                implementation(libs.library(("okhttp")))
                implementation(libs.library(("okhttp-logging")))
                implementationPlatform(libs.library("okhttp-bom"))
            }
        }
    }
}
