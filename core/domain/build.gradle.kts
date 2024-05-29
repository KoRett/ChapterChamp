plugins {
    alias(libs.plugins.chapterchamp.android.library)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.chapterchamp.android.dagger)
}

android {
    namespace = "com.korett.core.domain"
}

dependencies {
    api(project(":core:model"))

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}