package com.korett.bookdescription

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.korett.domain.repository.BookRepository
import com.korett.model.BookModel
import com.korett.ui.utils.LceState
import com.korett.ui.extension.MutableScreenStateFlow
import com.korett.ui.extension.ScreenStateFlow
import com.korett.ui.mapper.toBookUi
import com.korett.ui.model.BookModelUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import javax.inject.Singleton

class BookDescriptionViewModel(private val bookRepository: BookRepository) :
    ViewModel() {

    private val _screenState: MutableScreenStateFlow<BookModelUi> =
        MutableStateFlow(LceState.Initial)
    val screenState: ScreenStateFlow<BookModelUi> get() = _screenState.asStateFlow()

    fun getPopularBookById(id: Int) = bookRepository.getPopularBookById(id)
        .onStart {
            _screenState.value = LceState.Loading
        }
        .map {
            val favouriteBooks = bookRepository.getFavouriteBooks().first()
            it.toBookUi(it in favouriteBooks)
        }
        .onEach {
            _screenState.value = LceState.Content(it)
        }
        .catch {
            _screenState.value = LceState.Error(it)
        }
        .launchIn(viewModelScope)

    @Singleton
    class Factory @Inject constructor(
        private val bookRepository: BookRepository
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return BookDescriptionViewModel(
                bookRepository
            ) as T
        }

    }
}