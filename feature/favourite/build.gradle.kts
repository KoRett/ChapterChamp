plugins {
    alias(libs.plugins.chapterchamp.android.feature)
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "com.korett.favourite"
}

dependencies {

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}