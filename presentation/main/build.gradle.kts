import com.together.study.buildlogic.dsl.setNameSpace

plugins {
    id("com.together.study.buildlogic.convention.feature")
    id("com.together.study.buildlogic.convention.compose")
    id("com.together.study.buildlogic.primitive.hilt")
}

android {
    setNameSpace("main")
}

dependencies {

    implementation(projects.presentation.calendar)

    implementation(projects.core.common)
    implementation(projects.core.designsystem)
    implementation(projects.core.util)
    implementation(projects.presentation.calendar)
    implementation(projects.presentation.search)
    implementation(projects.presentation.study)
    implementation(projects.presentation.studydetail)
    implementation(projects.presentation.studymember)
    implementation(projects.presentation.studysettings)
    implementation(projects.domain.study)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
}
