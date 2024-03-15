pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
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

rootProject.name = "ChapterChamp"
include(":app")
include(":core:common")
include(":core:data")
include(":core:model")
include(":core:ui")
include(":feature:bookcatalog")
include(":feature:favourite")
