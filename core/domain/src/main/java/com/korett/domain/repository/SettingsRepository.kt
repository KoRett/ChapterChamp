package com.korett.domain.repository

import com.korett.model.ThemeModel

interface SettingsRepository {

    fun saveTheme(theme: ThemeModel)
    fun getTheme(): ThemeModel

}