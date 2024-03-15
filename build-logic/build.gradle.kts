plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.android.tools.common)
}

gradlePlugin {
    plugins.register("androidLibrary") {
        id = "chapterchamp.android.library"
        implementationClass = "com.korett.chapterchamp.AndroidLibraryConventionPlugin"
    }

    plugins.register("androidDagger") {
        id = "chapterchamp.android.dagger"
        implementationClass = "com.korett.chapterchamp.AndroidDaggerConventionPlugin"
    }

    plugins.register("androidFeature") {
        id = "chapterchamp.android.feature"
        implementationClass = "com.korett.chapterchamp.AndroidFeatureConventionPlugin"
    }

    plugins.register("androidApplication") {
        id = "chapterchamp.android.application"
        implementationClass = "com.korett.chapterchamp.AndroidApplicationConventionPlugin"
    }
}

