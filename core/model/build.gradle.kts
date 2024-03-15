plugins {
    alias(libs.plugins.chapterchamp.android.library)
}

android {
    namespace = "com.korett.core.model"
}

dependencies {

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}