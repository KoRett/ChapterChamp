plugins {
    alias(libs.plugins.chapterchamp.android.feature)
}

android {
    namespace = "com.korett.bookdescription"
}

dependencies {

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}