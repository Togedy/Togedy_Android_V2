package com.together.study.build_logic.convention

import com.together.study.build_logic.dsl.libs
import com.together.study.build_logic.dsl.androidApplicationExtension
import com.together.study.build_logic.dsl.configureAndroidLibrary
import com.together.study.build_logic.dsl.version
import com.together.study.build_logic.primitive.CommonAndroidPlugin
import com.together.study.build_logic.primitive.ComposePlugin
import com.together.study.build_logic.primitive.KotlinPlugin
import com.together.study.build_logic.primitive.TestPlugin
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
                    applicationId = "com.napzak.market"
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