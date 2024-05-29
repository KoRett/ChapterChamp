package com.korett.data.repository

import com.korett.data.database.dao.BookDao
import com.korett.data.database.mapper.toBookDomain
import com.korett.data.database.mapper.toBookEntity
import com.korett.model.BookModel
import com.korett.network.mapper.toBookDomain
import com.korett.network.retrofit.ChapterChampStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRepositoryImpl @Inject constructor(
    private val chapterChampStorage: ChapterChampStorage,
    private val bookDao: BookDao
) : com.korett.domain.repository.BookRepository {

    override fun getPopularBooks(): Flow<List<BookModel>> =
        flow {
            var books = bookDao.getAllBooks().map { it.toBookDomain() }

            if (books.isNotEmpty()) {
                emit(books)
            }

            books = chapterChampStorage.getPopularBooks().map { it.toBookDomain() }
            emit(books)

            val localBooks = bookDao.getAllBooks()
            books.forEach { remoteBook ->
                val localBook = localBooks.firstOrNull { remoteBook.id == it.id }
                if (localBook == null) {
                    bookDao.saveBook(remoteBook.toBookEntity(false))
                } else {
                    bookDao.saveBook(remoteBook.toBookEntity(localBook.isFavourite))
                }
            }
        }.flowOn(Dispatchers.IO)

    override fun getFavouriteBooks(): Flow<List<BookModel>> =
        bookDao.getFavouriteBooks().map { books -> books.map { it.toBookDomain() } }

    override suspend fun setBookIsFavourite(bookId: Int, isFavourite: Boolean) =
        bookDao.updateBookIsFavouriteById(bookId, isFavourite)

    override fun getPopularBookById(id: Int) =
        flow { emit(chapterChampStorage.getPopularBookById(id)) }
            .map { book ->
                BookModel(
                    id = book.id,
                    posterUrl = book.posterUrl,
                    title = book.title,
                    description = book.description,
                    author = book.author,
                    rating = book.rating
                )
            }
            .flowOn(Dispatchers.IO)
}