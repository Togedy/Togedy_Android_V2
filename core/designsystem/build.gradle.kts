import com.together.study.buildlogic.dsl.implementation
import com.together.study.buildlogic.dsl.setNameSpace

plugins {
    id("com.together.study.buildlogic.convention.feature")
    id("com.together.study.buildlogic.convention.compose")
}

android {
    setNameSpace("designsystem")
}

dependencies {

    implementation(projects.core.util)

    // Miscellaneous libraries
    implementation(libs.coil.compose)
    implementation(libs.lottie.compose)
}
