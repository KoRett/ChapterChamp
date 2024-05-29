package com.korett.ui.utils

sealed interface LceState<out T> {
    data object Initial : LceState<Nothing>
    data object Loading : LceState<Nothing>
    data class Error(val error: Throwable) : LceState<Nothing>
    data class Content<T>(val data: T) : LceState<T>
}