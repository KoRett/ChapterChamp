plugins {
    alias(libs.plugins.chapterchamp.android.feature)
}

android {
    namespace = "com.korett.booklist"
}

dependencies {

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}