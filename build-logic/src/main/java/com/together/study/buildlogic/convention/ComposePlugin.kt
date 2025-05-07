package com.together.study.buildlogic.convention

import com.together.study.buildlogic.dsl.androidExtension
import com.together.study.buildlogic.primitive.ComposePlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class ComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply<ComposePlugin>()

            androidExtension.apply {
                buildFeatures {
                    compose = true
                }
            }
        }
    }
}
