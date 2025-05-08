import com.together.study.buildlogic.dsl.setNameSpace

plugins {
    id("com.together.study.buildlogic.convention.feature")
    id("com.together.study.buildlogic.primitive.hilt")
}

android {
    setNameSpace("local")
}

dependencies {

    implementation(libs.androidx.datastore)
}