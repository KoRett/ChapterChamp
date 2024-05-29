package com.korett.ui.mapper

import com.korett.model.BookModel
import com.korett.ui.model.BookModelUi

fun BookModel.toBookUi(isFavourite: Boolean): BookModelUi =
    BookModelUi(
        id = id,
        posterUrl = posterUrl,
        title = title,
        description = description,
        author = author,
        rating = rating,
        isFavourite = isFavourite
    )

fun BookModelUi.toBookDomain(): BookModel =
    BookModel(
        id = id,
        posterUrl = posterUrl,
        title = title,
        description = description,
        author = author,
        rating = rating
    )