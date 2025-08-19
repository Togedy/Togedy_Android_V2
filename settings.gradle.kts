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
)
include(
    ":domain:dummy",
    ":domain:calendar",
    ":domain:search",
)
include(
    ":presentation:main",
    ":presentation:dummy",
    ":presentation:calendar",
    ":presentation:search",
)