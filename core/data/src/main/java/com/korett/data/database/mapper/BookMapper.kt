package com.korett.data.database.mapper

import com.korett.data.database.entity.BookEntity
import com.korett.model.BookModel

fun BookEntity.toBookDomain(): BookModel =
    BookModel(
        id = id,
        posterUrl = posterUrl,
        title = title,
        description = description,
        author = author,
        rating = rating
    )

fun BookModel.toBookEntity(isFavourite: Boolean): BookEntity =
    BookEntity(
        id = id,
        posterUrl = posterUrl,
        title = title,
        description = description,
        author = author,
        rating = rating,
        isFavourite = isFavourite
    )