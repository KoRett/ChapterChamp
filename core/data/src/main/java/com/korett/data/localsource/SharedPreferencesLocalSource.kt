package com.korett.data.localsource

import android.content.Context
import android.content.SharedPreferences
import com.korett.data.localsource.model.ThemeModelData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesLocalSource @Inject constructor(context: Context) : LocalSource {

    private val pref: SharedPreferences =
        context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
    private val editor get() = pref.edit()

    override fun saveTheme(theme: ThemeModelData) {
        editor.putInt(APP_THEME, theme.value).commit()
    }

    override fun getTheme(): ThemeModelData =
        (ThemeModelData from pref.getInt(APP_THEME, ThemeModelData.System.value))!!

    override fun saveSearch(search: String) {
        val searches = pref.getStringSet(SEARCHES, setOf())!!.toMutableSet()
        while (searches.size >= 10) {
            searches.remove(searches.last())
        }
        searches.add(search)
        editor.putStringSet(SEARCHES, searches.toSet()).commit()
    }

    override fun clearSearch() {
        editor.remove(SEARCHES).commit()
    }

    override fun getSearches(): List<String> = pref.getStringSet(SEARCHES, setOf())!!.toList()

    companion object {
        const val APP_PREFERENCES = "APP_PREFERENCES"
        const val APP_THEME = "APP_THEME"
        const val SEARCHES = "SEARCHES"
    }
}
