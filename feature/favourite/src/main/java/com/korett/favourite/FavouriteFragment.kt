package com.korett.favourite

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.korett.bookcatalog.navigation.FavouriteNavigation
import com.korett.core.ui.databinding.ErrorStateBinding
import com.korett.core.ui.databinding.LoadingStateBinding
import com.korett.favourite.databinding.FragmentFavouriteBinding
import com.korett.favourite.di.FavouriteSubcomponentProvider
import com.korett.ui.BookShortcutAdapter
import com.korett.ui.model.BookModelUi
import com.korett.ui.utils.LceState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Provider

class FavouriteFragment : Fragment(R.layout.fragment_favourite) {

    private val binding: FragmentFavouriteBinding by viewBinding()
    private val bindingError: ErrorStateBinding by viewBinding()
    private val bindingLoading: LoadingStateBinding by viewBinding()

    @Inject
    lateinit var viewModelFactory: Provider<FavouriteViewModel.Factory>
    private val viewModel: FavouriteViewModel by viewModels { viewModelFactory.get() }

    private var adapter: BookShortcutAdapter? = null

    @Inject
    lateinit var favouriteNavigation: FavouriteNavigation

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val favouriteSubcomponent =
            (requireActivity().application as FavouriteSubcomponentProvider).provideFavouriteSubcomponent()

        favouriteSubcomponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initAdapter()

        bindingError.buttonRefresh.setOnClickListener {
            viewModel.getFavouriteBookShortcuts()
        }

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach(::render)
            .launchIn(lifecycleScope)
    }

    private fun initAdapter() {
        adapter = BookShortcutAdapter(
            itemClick = {
                findNavController().navigate(favouriteNavigation.directionToBookDescription(it.id))
            })

        binding.rvBooksShortcuts.adapter = adapter
    }

    private fun render(lceState: LceState<List<BookModelUi>>) {
        binding.rvBooksShortcuts.isVisible = false
        bindingError.textError.isVisible = false
        bindingError.imageError.isVisible = false
        bindingError.buttonRefresh.isVisible = false
        bindingLoading.progressBar.isVisible = false

        when (lceState) {
            is LceState.Error -> {
                bindingError.textError.isVisible = false
                bindingError.imageError.isVisible = false
                bindingError.buttonRefresh.isVisible = false
            }

            LceState.Initial -> {
                bindingLoading.progressBar.isVisible = false
            }

            LceState.Loading -> {
                bindingLoading.progressBar.isVisible = false
            }

            is LceState.Content -> {
                binding.rvBooksShortcuts.isVisible = true
                adapter!!.submitList(lceState.data)
            }
        }
    }


    override fun onDestroyView() {
        adapter = null
        super.onDestroyView()
    }

}