package com.korett.network.mapper

import com.korett.model.BookModel
import com.korett.network.model.BookNetwork


fun BookNetwork.toBookDomain(): BookModel =
    BookModel(
        id = id,
        posterUrl = posterUrl,
        title = title,
        description = description,
        author = author,
        rating = rating
    )

fun BookModel.toBookNetwork(): BookNetwork =
    BookNetwork(
        id = id,
        posterUrl = posterUrl,
        title = title,
        description = description,
        author = author,
        rating = rating
    )