plugins {
    alias(libs.plugins.chapterchamp.android.library)
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "com.korett.core.data"
}

dependencies {

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}