package com.korett.ui.model

data class BookModelUi(
    val id: Int,
    val posterUrl: String,
    val title: String,
    val description: String,
    val author: String,
    val rating: Float,
    val isFavourite: Boolean
)