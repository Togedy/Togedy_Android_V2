import com.together.study.buildlogic.dsl.setNameSpace

plugins {
    id("com.together.study.buildlogic.convention.feature")
    id("com.together.study.buildlogic.convention.compose")
    id("com.together.study.buildlogic.primitive.hilt")
}
android {
    setNameSpace("presentation.study")
}

dependencies {

    implementation(projects.core.common)
    implementation(projects.core.designsystem)
    implementation(projects.core.util)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.coil.compose)
    implementation(libs.timber)
}