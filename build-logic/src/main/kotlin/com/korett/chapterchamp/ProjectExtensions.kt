package com.korett.chapterchamp

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

private const val CATALOG_NAME = "libs"

val Project.libs
    get() = extensions.getByType<VersionCatalogsExtension>().named(CATALOG_NAME)