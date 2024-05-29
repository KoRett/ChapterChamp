package com.korett.data.localsource.model

private const val LIGHT = 1
private const val DARK = 2
private const val SYSTEM = -1

enum class ThemeModelData(val value: Int) {
    Light(LIGHT),
    Dark(DARK),
    System(SYSTEM);

    companion object {
        infix fun from(value: Int): ThemeModelData? = entries.firstOrNull { it.value == value }
    }
}