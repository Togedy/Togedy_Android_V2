pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://devrepo.kakao.com/nexus/content/groups/public/") }
    }
}

rootProject.name = "Togedy_Android_V2"
include(
    ":app",
    ":core:common",
    ":core:designsystem",
    ":core:util",
)
include(
    ":data:local",
    ":data:remote",
    ":data:calendar",
    ":data:search",
    ":data:dummy",
    ":data:study",
    ":data:chatbot",
    ":data:planner",
    ":data:user",
    ":data:mypage",
    ":data:gallery",
    ":data:auth",
)
include(
    ":domain:dummy",
    ":domain:calendar",
    ":domain:study",
    ":domain:planner",
    ":domain:search",
    ":domain:auth",
    ":domain:user",
    ":domain:mypage",
    ":domain:gallery",
    ":domain:chatbot",
)
include(
    ":presentation:main",
    ":presentation:dummy",
    ":presentation:calendar",
    ":presentation:search",
    ":presentation:study",
    ":presentation:studydetail",
    ":presentation:studysettings",
    ":presentation:studymember",
    ":presentation:studyupdate",
    ":presentation:planner",
    ":presentation:chatbot",
    ":presentation:timer",
    ":presentation:login",
    ":presentation:mypage",
    ":presentation:gallery",
)