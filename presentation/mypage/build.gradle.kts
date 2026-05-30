plugins {
    id("com.together.study.buildlogic.convention.feature")
    id("com.together.study.buildlogic.convention.compose")
    id("com.together.study.buildlogic.primitive.hilt")
}

android {
    namespace = "com.together.study.mypage"
}

dependencies {

    implementation(projects.core.common)
    implementation(projects.core.designsystem)
    implementation(projects.core.util)

    implementation(projects.domain.auth)
    implementation(projects.domain.study)
    implementation(projects.domain.user)
    implementation(projects.domain.mypage)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.coil.compose)
    implementation(libs.timber)

    implementation(libs.richtext.ui.material3)
    implementation(libs.richtext.commonmark)
}