plugins {
    id("com.together.study.buildlogic.convention.application")
    id("com.together.study.buildlogic.primitive.hilt")
}

android {
    namespace = "com.together.study"
}

dependencies {

    implementation(projects.presentation.main)
}
