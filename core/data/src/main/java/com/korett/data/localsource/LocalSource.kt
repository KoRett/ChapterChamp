package com.korett.data.localsource

import com.korett.data.localsource.model.ThemeModelData

interface LocalSource {
    fun getTheme(): ThemeModelData
    fun saveTheme(theme: ThemeModelData)
    fun getSearches(): List<String>
    fun saveSearch(search: String)
    fun clearSearch()
}