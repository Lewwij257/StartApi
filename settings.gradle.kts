pluginManagement {
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
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "StartApi"
include(":app")

include(":features:welcome")
include(":features:sign-up")
include(":theme")
include(":features:sign-in")
include(":features:home")
include(":data")
include(":navigation")
include(":core")
include(":features:feed")
include(":features:projects")
include(":features:messenger")
include(":features:settings")
