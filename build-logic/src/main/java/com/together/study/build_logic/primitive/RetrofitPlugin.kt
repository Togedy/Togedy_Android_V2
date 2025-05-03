package com.together.study.build_logic.primitive

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import com.together.study.build_logic.dsl.implementation
import com.together.study.build_logic.dsl.library
import com.together.study.build_logic.dsl.libs

class RetrofitPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                implementation(libs.library(("retrofit")))
                implementation(libs.library(("retrofit2-kotlinx-serialization-converter")))
            }
        }
    }
}
