plugins {
    `kotlin-dsl`
}

group = "com.together.study.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.gradle)
    compileOnly(libs.kotlin.gradle)
    compileOnly(libs.compose.compiler.gradle)
}

gradlePlugin {
    plugins {
        register("com.together.study.buildlogic.primitive.CommonAndroidPlugin") {
            id = "com.together.study.buildlogic.primitive.common"
            implementationClass = "com.together.study.buildlogic.primitive.CommonAndroidPlugin"
        }

        register("com.together.study.buildlogic.primitive.ComposePlugin") {
            id = "com.together.study.buildlogic.primitive.compose"
            implementationClass = "com.together.study.buildlogic.primitive.ComposePlugin"
        }

        register("com.together.study.buildlogic.primitive.HiltPlugin") {
            id = "com.together.study.buildlogic.primitive.hilt"
            implementationClass = "com.together.study.buildlogic.primitive.HiltPlugin"
        }

        register("com.together.study.buildlogic.primitive.KotlinPlugin") {
            id = "com.together.study.buildlogic.primitive.kotlin"
            implementationClass = "com.together.study.buildlogic.primitive.KotlinPlugin"
        }

        register("com.together.study.buildlogic.primitive.RetrofitPlugin") {
            id = "com.together.study.buildlogic.primitive.retrofit"
            implementationClass = "com.together.study.buildlogic.primitive.RetrofitPlugin"
        }

        register("com.together.study.buildlogic.primitive.OkHttpPlugin") {
            id = "com.together.study.buildlogic.primitive.okhttp"
            implementationClass = "com.together.study.buildlogic.primitive.OkHttpPlugin"
        }

        register("com.together.study.buildlogic.primitive.TestPlugin") {
            id = "com.together.study.buildlogic.primitive.test"
            implementationClass = "com.together.study.buildlogic.primitive.TestPlugin"
        }

        register("com.together.study.buildlogic.convention.ApplicationPlugin") {
            id = "com.together.study.buildlogic.convention.application"
            implementationClass = "com.together.study.buildlogic.convention.ApplicationPlugin"
        }

        register("com.together.study.buildlogic.convention.ComposePlugin") {
            id = "com.together.study.buildlogic.convention.compose"
            implementationClass = "com.together.study.buildlogic.convention.ComposePlugin"
        }

        register("com.together.study.buildlogic.convention.FeaturePlugin") {
            id = "com.together.study.buildlogic.convention.feature"
            implementationClass = "com.together.study.buildlogic.convention.FeaturePlugin"
        }

        register("com.together.study.buildlogic.convention.KotlinJvmPlugin") {
            id = "com.together.study.buildlogic.convention.kotlin"
            implementationClass = "com.together.study.buildlogic.convention.KotlinJvmPlugin"
        }
    }
}