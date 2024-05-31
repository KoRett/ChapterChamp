package com.korett.domain.use_case

import com.korett.domain.repository.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchPopularBooksUseCase @Inject constructor(private val bookRepository: BookRepository) {
    operator fun invoke(query: String) = bookRepository.getPopularBooks()
        .map { bookShortcutList ->
            bookShortcutList.filter { query.lowercase() in it.title.lowercase() }
        }
        .flowOn(Dispatchers.Default)
}