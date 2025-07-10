import java.util.Properties

plugins {
    id("com.together.study.buildlogic.convention.feature")
    id("com.together.study.buildlogic.primitive.hilt")
    id("com.together.study.buildlogic.primitive.retrofit")
    id("com.together.study.buildlogic.primitive.okhttp")
}

val properties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}
android {
    namespace = "data.remote"

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", properties.getProperty("base.url"))
        }
    }
}

dependencies {

    implementation(projects.data.local)
    implementation(libs.timber)
}
