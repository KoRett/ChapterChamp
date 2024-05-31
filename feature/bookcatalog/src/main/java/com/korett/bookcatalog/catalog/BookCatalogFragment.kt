package com.korett.bookcatalog.catalog

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.korett.bookcatalog.R
import com.korett.bookcatalog.databinding.FragmentBookCatalogBinding
import com.korett.bookcatalog.di.BookCatalogSubcomponentProvider
import com.korett.bookcatalog.navigation.BookCatalogNavigation
import com.korett.core.ui.databinding.ErrorStateBinding
import com.korett.core.ui.databinding.LoadingStateBinding
import com.korett.ui.BookShortcutAdapter
import com.korett.ui.model.BookModelUi
import com.korett.ui.utils.LceState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Provider

class BookCatalogFragment : Fragment(R.layout.fragment_book_catalog) {

    private val binding: FragmentBookCatalogBinding by viewBinding()
    private val bindingError: ErrorStateBinding by viewBinding()
    private val bindingLoading: LoadingStateBinding by viewBinding()

    @Inject
    lateinit var viewModelFactory: Provider<BookCatalogViewModel.Factory>
    private val viewModel: BookCatalogViewModel by viewModels { viewModelFactory.get() }

    private var adapter: BookShortcutAdapter? = null

    @Inject
    lateinit var bookCatalogNavigation: BookCatalogNavigation

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val bookCatalogSubcomponent =
            (requireActivity().application as BookCatalogSubcomponentProvider).provideBookCatalogSubcomponent()

        bookCatalogSubcomponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbarCatalog.inflateMenu(R.menu.menu_book_catalog)

        binding.toolbarCatalog.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_search_button -> findNavController().navigate(bookCatalogNavigation.directionToSearch())
            }
            true
        }

        bindingError.buttonRefresh.setOnClickListener {
            viewModel.getPopularBookShortcuts()
        }

        initAdapter()
    }

    private fun initAdapter() {
        adapter = BookShortcutAdapter(
            itemClick = {
                findNavController().navigate(bookCatalogNavigation.directionToBookDescription(it.id))
            },
            longItemClick = {
                viewModel.setBookFavourite(it.id, !it.isFavourite)
            })

        binding.rvBooksShortcuts.adapter = adapter

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach(::render)
            .launchIn(lifecycleScope)
    }

    private fun render(lceState: LceState<List<BookModelUi>>) {
        binding.rvBooksShortcuts.visibility = View.GONE
        bindingError.textError.visibility = View.GONE
        bindingError.imageError.visibility = View.GONE
        bindingError.buttonRefresh.visibility = View.GONE
        bindingLoading.progressBar.visibility = View.GONE

        when (lceState) {
            is LceState.Error -> {
                bindingError.textError.visibility = View.VISIBLE
                bindingError.imageError.visibility = View.VISIBLE
                bindingError.buttonRefresh.visibility = View.VISIBLE
            }

            LceState.Initial -> {
                bindingLoading.progressBar.visibility = View.VISIBLE
            }

            LceState.Loading -> {
                bindingLoading.progressBar.visibility = View.VISIBLE
            }

            is LceState.Content -> {
                binding.rvBooksShortcuts.visibility = View.VISIBLE
                adapter!!.submitList(lceState.data)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }
}