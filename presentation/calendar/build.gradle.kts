import com.together.study.buildlogic.dsl.setNameSpace

plugins {
    id("com.together.study.buildlogic.convention.feature")
    id("com.together.study.buildlogic.primitive.hilt")
    id("com.together.study.buildlogic.convention.compose")
}

android {
    setNameSpace("presentation.calendar")
}

dependencies {

    implementation(projects.core.designsystem)
    implementation(projects.core.common)
    implementation(projects.core.util)

    implementation(projects.domain.calendar)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.coil.compose)
    implementation(libs.timber)
}