package com.korett.data.repository

import com.korett.data.localsource.LocalSource
import com.korett.data.localsource.model.ThemeModelData
import com.korett.domain.repository.SettingsRepository
import com.korett.model.ThemeModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    private val localSource: LocalSource
) : SettingsRepository {

    override fun saveTheme(theme: ThemeModel) {
        val themeData = when (theme) {
            ThemeModel.Dark -> ThemeModelData.Dark
            ThemeModel.Light -> ThemeModelData.Light
            ThemeModel.System -> ThemeModelData.System
        }
        localSource.saveTheme(themeData)
    }

    override fun getTheme(): ThemeModel {
        val theme = when (localSource.getTheme()) {
            ThemeModelData.Light -> ThemeModel.Light
            ThemeModelData.Dark -> ThemeModel.Dark
            ThemeModelData.System -> ThemeModel.System
        }
        return theme
    }
}