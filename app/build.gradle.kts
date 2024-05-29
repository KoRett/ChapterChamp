plugins {
    alias(libs.plugins.chapterchamp.android.application)
}

android {
    namespace = "com.korett.chapterchamp"
}

dependencies {
    implementation(project(":feature:bookcatalog"))
    implementation(project(":feature:favourite"))
    implementation(project(":feature:bookdescription"))
    implementation(project(":feature:settings"))

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}