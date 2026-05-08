@file:Suppress("UnstableApiUsage")

include(":core:common")


include(":core:testing")


include(":feature:trip:api")


include(":feature:trip:impl")


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

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "redknot"
include(":app")
include(":feature:trip:api")
include(":feature:trip:impl")
include(":core:common")
include(":core:designsystem")
include(":core:testing")
