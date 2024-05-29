plugins {
    alias(libs.plugins.chapterchamp.android.library)
    alias(libs.plugins.chapterchamp.android.dagger)
}

android {
    namespace = "com.korett.core.network"
}

dependencies {
    api(libs.core.ktx)
    api(libs.bundles.retrofit)

    implementation(project(":core:model"))

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}