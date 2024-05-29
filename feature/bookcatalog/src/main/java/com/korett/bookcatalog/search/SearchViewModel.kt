package com.korett.bookcatalog.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.korett.domain.repository.BookRepository
import com.korett.domain.use_case.SearchPopularBooksUseCase
import com.korett.ui.extension.MutableScreenStateFlow
import com.korett.ui.extension.ScreenStateFlow
import com.korett.ui.mapper.toBookUi
import com.korett.ui.model.BookModelUi
import com.korett.ui.utils.LceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.single
import javax.inject.Inject
import javax.inject.Singleton

class SearchViewModel(
    private val searchPopularBooksUseCase: SearchPopularBooksUseCase,
    private val bookRepository: BookRepository
) : ViewModel() {

    private val _screenState: MutableScreenStateFlow<List<BookModelUi>> =
        MutableStateFlow(LceState.Initial)
    val screenState: ScreenStateFlow<List<BookModelUi>> get() = _screenState.asStateFlow()

    val searchQueryState = MutableStateFlow("")

    init {
        listenToSearchState()
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    fun listenToSearchState() {
        searchQueryState
            .filter { it.isNotBlank() }
            .debounce(500)
            .mapLatest { query ->
                _screenState.value = LceState.Loading
                val favouriteBooks = bookRepository.getFavouriteBooks().first()
                searchPopularBooksUseCase(query).last().map { it.toBookUi(it in favouriteBooks) }
            }
            .flowOn(Dispatchers.Default)
            .onEach {
                _screenState.value = LceState.Content(it)
            }
            .catch {
                _screenState.value = LceState.Error(it)
            }
            .launchIn(viewModelScope)
    }

    @Singleton
    class Factory @Inject constructor(
        private val searchPopularBooksUseCase: SearchPopularBooksUseCase,
        private val bookRepository: BookRepository
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchViewModel(
                searchPopularBooksUseCase,
                bookRepository
            ) as T
        }

    }
}