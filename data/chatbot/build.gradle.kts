import com.together.study.buildlogic.dsl.setNameSpace

plugins {
    id("com.together.study.buildlogic.convention.feature")
    id("com.together.study.buildlogic.primitive.hilt")
    id("com.together.study.buildlogic.primitive.retrofit")
    id("com.together.study.buildlogic.primitive.okhttp")
}

android {
    setNameSpace("data.chatbot")
}

dependencies {
    implementation(projects.data.remote)
    implementation(projects.data.local)
    implementation(projects.domain.chatbot)
    implementation(libs.timber)
}
