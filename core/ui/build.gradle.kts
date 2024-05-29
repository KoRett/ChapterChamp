plugins {
    alias(libs.plugins.chapterchamp.android.library)
}

android {
    namespace = "com.korett.core.ui"

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    api(libs.core.ktx)
    api(libs.appcompat)
    api(libs.material)
    api(libs.constraintlayout)
    api(libs.androidx.navigation.fragment.ktx)
    api(libs.androidx.navigation.ui.ktx)

    api(libs.glide)
    api(libs.glide.ksp)

    api(libs.bundles.bindingDelegate)

    api(project(":core:data"))
    implementation(project(":core:model"))

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}