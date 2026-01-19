package com.together.study.buildlogic.convention

import com.together.study.buildlogic.dsl.androidApplicationExtension
import com.together.study.buildlogic.dsl.configureAndroidLibrary
import com.together.study.buildlogic.dsl.libs
import com.together.study.buildlogic.dsl.version
import com.together.study.buildlogic.primitive.CommonAndroidPlugin
import com.together.study.buildlogic.primitive.ComposePlugin
import com.together.study.buildlogic.primitive.KotlinPlugin
import com.together.study.buildlogic.primitive.TestPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class ApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
            }

            apply<KotlinPlugin>()
            apply<CommonAndroidPlugin>()
            apply<ComposePlugin>()
            apply<TestPlugin>()
            configureAndroidLibrary()

            androidApplicationExtension {
                composeOptions {
                    kotlinCompilerExtensionVersion =
                        libs.findVersion("kotlinCompilerExtensionVersion").get().requiredVersion
                }

                defaultConfig {
                    applicationId = "com.together.study"
                    versionCode = libs.version("versionCode").toInt()
                    versionName = libs.version("versionName")
                }

                buildTypes {
                    release {
                        isMinifyEnabled = false
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                }
            }
        }
    }
}