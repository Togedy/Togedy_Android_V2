import com.together.study.buildlogic.dsl.setNameSpace

plugins {
    id("com.together.study.buildlogic.convention.feature")
    id("com.together.study.buildlogic.convention.compose")
}

android {
    setNameSpace("main")
}

dependencies {

    implementation(projects.core.designsystem)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
}
