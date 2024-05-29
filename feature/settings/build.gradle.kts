plugins {
    alias(libs.plugins.chapterchamp.android.feature)
}

android {
    namespace = "com.korett.settings"
}

dependencies {
    api(project(":core:data"))

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}