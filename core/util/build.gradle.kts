import com.together.study.buildlogic.dsl.setNameSpace

plugins {
    id("com.together.study.buildlogic.convention.feature")
    id("com.together.study.buildlogic.convention.compose")
    id("com.together.study.buildlogic.primitive.okhttp")
    id("com.together.study.buildlogic.primitive.retrofit")
}

android {
    setNameSpace("util")
}
