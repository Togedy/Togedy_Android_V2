plugins {
    id("com.together.study.buildlogic.convention.application")
    id("com.together.study.buildlogic.primitive.hilt")
}

android {
    namespace = "com.together.study"
}

dependencies {

    implementation(projects.data.remote)
    implementation(projects.data.local)
    implementation(projects.data.dummy)
    implementation(projects.data.calendar)
    implementation(projects.data.search)
    implementation(projects.presentation.main)

    implementation(libs.androidx.appcompat)
    implementation(libs.timber)
}
