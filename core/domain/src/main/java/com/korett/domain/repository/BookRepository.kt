package com.korett.domain.repository

import com.korett.model.BookModel
import kotlinx.coroutines.flow.Flow


interface BookRepository {
    fun getPopularBooks(): Flow<List<BookModel>>
    fun getPopularBookById(id: Int): Flow<BookModel>
    fun getFavouriteBooks(): Flow<List<BookModel>>
    suspend fun setBookIsFavourite(bookId: Int, isFavourite: Boolean)
}