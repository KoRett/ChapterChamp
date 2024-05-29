package com.korett.bookcatalog.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.korett.domain.repository.BookRepository
import com.korett.ui.extension.MutableScreenStateFlow
import com.korett.ui.extension.ScreenStateFlow
import com.korett.ui.mapper.toBookUi
import com.korett.ui.model.BookModelUi
import com.korett.ui.utils.LceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

class BookCatalogViewModel(private val bookRepository: BookRepository) : ViewModel() {

    private val _screenState: MutableScreenStateFlow<List<BookModelUi>> =
        MutableStateFlow(LceState.Initial)
    val screenState: ScreenStateFlow<List<BookModelUi>> get() = _screenState.asStateFlow()

    private var bookJob: Job? = null

    init {
        getPopularBookShortcuts()
    }


    fun getPopularBookShortcuts() {
        bookJob?.cancel()
        bookJob = bookRepository.getPopularBooks()
            .onEach { books ->
                val favouriteBooks = bookRepository.getFavouriteBooks().first()
                val booksUi = books.map { it.toBookUi(it in favouriteBooks) }
                _screenState.value = LceState.Content(booksUi)
            }
            .catch {
                _screenState.value = LceState.Error(it)
            }
            .launchIn(viewModelScope)
    }

    fun setBookFavourite(bookId: Int, isFavourite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            bookRepository.setBookIsFavourite(bookId, isFavourite)
        }
        getPopularBookShortcuts()
    }

    @Singleton
    class Factory @Inject constructor(
        private val bookRepository: BookRepository
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return BookCatalogViewModel(
                bookRepository
            ) as T
        }

    }
}