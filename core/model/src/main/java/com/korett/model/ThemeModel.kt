package com.korett.model

sealed class ThemeModel {
    data object Light : ThemeModel()
    data object Dark : ThemeModel()
    data object System : ThemeModel()
}