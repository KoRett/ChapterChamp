plugins {
    alias(libs.plugins.chapterchamp.android.library)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.chapterchamp.android.dagger)
}

android {
    namespace = "com.korett.core.data"
}

dependencies {
    api(project(":core:network"))
    api(project(":core:domain"))

    implementation(libs.room)
    implementation(libs.room.ktx)
    ksp(libs.roomCompiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}