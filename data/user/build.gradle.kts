import com.together.study.buildlogic.dsl.setNameSpace

plugins {
    id("com.together.study.buildlogic.convention.feature")
    id("com.together.study.buildlogic.primitive.hilt")
    id("com.together.study.buildlogic.primitive.retrofit")
    id("com.together.study.buildlogic.primitive.okhttp")
}

android {
    setNameSpace("data.user")
}

dependencies {
    implementation(projects.core.util)
    implementation(projects.core.common)

    implementation(projects.data.remote)
    implementation(projects.domain.user)
    implementation(libs.timber)
}
