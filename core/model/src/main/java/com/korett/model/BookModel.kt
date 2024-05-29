package com.korett.model

data class BookModel(
    val id: Int,
    val posterUrl: String,
    val title: String,
    val description: String,
    val author: String,
    val rating: Float
)
