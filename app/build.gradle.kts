import java.util.Properties

plugins {
    id("com.together.study.buildlogic.convention.application")
    id("com.together.study.buildlogic.primitive.hilt")
    alias(libs.plugins.ksp)
}

val localProps = Properties().apply {
    val f = File(rootDir, "local.properties")
    if (f.exists()) f.inputStream().use { load(it) }
}

val kakaoNativeKey: String =
    providers.gradleProperty("KAKAO_APP_KEY").orNull
        ?: System.getenv("KAKAO_APP_KEY")
        ?: localProps.getProperty("kakao.app.key")
        ?: throw GradleException("KAKAO_APP_KEY (or local kakao.app.key) is missing")

android {
    namespace = "com.together.study"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigField("String", "KAKAO_APP_KEY", "\"$kakaoNativeKey\"")
        manifestPlaceholders["kakaoScheme"] = "kakao$kakaoNativeKey"
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
            applicationIdSuffix = ".debug"
            manifestPlaceholders["appName"] = "@string/app_name_dev"
            manifestPlaceholders["appIcon"] = "@mipmap/ic_app_dev"
        }
        // 추후 릴리즈 시 사용
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            manifestPlaceholders["appName"] = "@string/app_name"
            manifestPlaceholders["appIcon"] = "@drawable/ic_app"
        }
    }
}

configurations.all {
    exclude(group = "com.intellij", module = "annotations")
}

dependencies {

    implementation(projects.data.remote)
    implementation(projects.data.local)
    implementation(projects.data.dummy)
    implementation(projects.data.calendar)
    implementation(projects.data.study)
    implementation(projects.data.search)
    implementation(projects.data.auth)
    implementation(projects.data.gallery)
    implementation(projects.data.planner)
    implementation(projects.data.user)
    implementation(projects.data.mypage)
    implementation(projects.data.timer)
    implementation(projects.presentation.main)
    implementation(projects.presentation.timer)

    implementation(libs.androidx.appcompat)
    implementation(libs.timber)

    implementation(libs.kakao.v2.user)
    implementation(libs.kakao.v2.common)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.multidex)
    implementation(libs.lottie.compose)
}
