package com.korett.bookcatalog.search

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.korett.bookcatalog.R
import com.korett.bookcatalog.databinding.FragmentSearchBinding
import com.korett.bookcatalog.di.BookCatalogSubcomponentProvider
import com.korett.bookcatalog.navigation.BookSearchNavigation
import com.korett.bookcatalog.search.adapter.SearchAdapter
import com.korett.bookcatalog.search.adapter.QueryModel
import com.korett.core.ui.databinding.ErrorStateBinding
import com.korett.core.ui.databinding.LoadingStateBinding
import com.korett.data.localsource.LocalSource
import com.korett.ui.BookShortcutAdapter
import com.korett.ui.model.BookModelUi
import com.korett.ui.utils.LceState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Provider


class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding: FragmentSearchBinding by viewBinding()
    private val bindingError: ErrorStateBinding by viewBinding()
    private val bindingLoading: LoadingStateBinding by viewBinding()

    @Inject
    lateinit var viewModelFactory: Provider<SearchViewModel.Factory>
    private val viewModel: SearchViewModel by viewModels { viewModelFactory.get() }

    private var bookAdapter: BookShortcutAdapter? = null
    private var searchAdapter: SearchAdapter? = null

    @Inject
    lateinit var localSource: LocalSource

    @Inject
    lateinit var searchNavigation: BookSearchNavigation

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val bookCatalogSubcomponent =
            (requireActivity().application as BookCatalogSubcomponentProvider).provideBookCatalogSubcomponent()

        bookCatalogSubcomponent.inject(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()

        binding.tbSearch.setupWithNavController(findNavController())

        binding.bookSearch.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
            .setOnClickListener {
                if (binding.bookSearch.query.isEmpty()) {
                    binding.bookSearch.isIconified = true;
                } else {
                    hideKeyboard()
                    binding.bookSearch.setQuery("", false);
                }
            }

        binding.bookSearch.setOnQueryTextFocusChangeListener { v, hasFocus ->
            binding.recyclerViewQueries.visibility = View.GONE
            if (hasFocus) {
                val queries = localSource.getSearches()
                    .mapIndexed { index, query ->
                        QueryModel(index, query)
                    }

                if (queries.isNotEmpty()) {
                    binding.recyclerViewQueries.visibility = View.VISIBLE
                }

                searchAdapter!!.submitList(queries)
            }
        }


        binding.bookSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchQueryState.tryEmit(query ?: "")
                hideKeyboard()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchQueryState.tryEmit(newText ?: "")

                val queries = localSource.getSearches()
                    .filter {
                        if (!newText.isNullOrBlank()) {
                            newText.lowercase() in it.lowercase()
                        } else {
                            true
                        }
                    }
                    .mapIndexed { index, query ->
                        QueryModel(index, query)
                    }

                searchAdapter!!.submitList(queries)

                return true
            }
        })

        bindingError.buttonRefresh.setOnClickListener {
            viewModel.listenToSearchState()
        }

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach(::render)
            .launchIn(lifecycleScope)
    }

    private fun initAdapter() {
        bookAdapter = BookShortcutAdapter(itemClick = {
            localSource.saveSearch(it.title)
            findNavController().navigate(searchNavigation.directionToBookDescription(it.id))
        })

        searchAdapter = SearchAdapter {
            binding.bookSearch.setQuery(it.query, true)
        }

        binding.rvBookShortcuts.adapter = bookAdapter
        binding.recyclerViewQueries.adapter = searchAdapter
    }

    private fun render(state: LceState<List<BookModelUi>>) {
        hideAllStates()

        when (state) {
            is LceState.Error -> {
                changeErrorStateVisibility(View.VISIBLE)
            }

            LceState.Initial -> {
                binding.textEmptyQuery.visibility = View.VISIBLE
            }

            LceState.Loading -> {
                bindingLoading.progressBar.visibility = View.VISIBLE
            }

            is LceState.Content -> {
                if (state.data.isNotEmpty()) {
                    binding.rvBookShortcuts.visibility = View.VISIBLE
                    bookAdapter!!.submitList(state.data)
                } else {
                    binding.textEmptyResult.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun changeErrorStateVisibility(visibility: Int) {
        bindingError.textError.visibility = visibility
        bindingError.imageError.visibility = visibility
        bindingError.buttonRefresh.visibility = visibility
    }

    private fun hideAllStates() {
        binding.rvBookShortcuts.visibility = View.GONE

        changeErrorStateVisibility(View.GONE)

        bindingLoading.progressBar.visibility = View.GONE

        binding.textEmptyQuery.visibility = View.GONE
        binding.textEmptyResult.visibility = View.GONE
    }

    private fun hideKeyboard() {
        with(requireActivity()) {
            currentFocus?.let {
                val imm =
                    getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(it.windowToken, 0)
                it.clearFocus()
            }
        }
    }

    override fun onDestroyView() {
        bookAdapter = null
        searchAdapter = null
        super.onDestroyView()
    }

}