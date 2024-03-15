package com.korett.chapterchamp

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("chapterchamp.android.library")
                apply("chapterchamp.android.dagger")
            }

            extensions.configure<LibraryExtension> {
                buildFeatures {
                    viewBinding = true
                }
            }

            dependencies {
                "implementation"(project(":core:ui"))
                "implementation"(project(":core:data"))
                "implementation"(project(":core:model"))
            }
        }
    }

}