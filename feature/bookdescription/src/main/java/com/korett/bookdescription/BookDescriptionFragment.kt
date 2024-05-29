package com.korett.bookdescription

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.korett.bookdescription.databinding.FragmentBookDescriptionBinding
import com.korett.bookdescription.di.BookDescriptionSubcomponentProvider
import com.korett.core.ui.databinding.ErrorStateBinding
import com.korett.core.ui.databinding.LoadingStateBinding
import com.korett.model.BookModel
import com.korett.ui.model.BookModelUi
import com.korett.ui.utils.LceState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Provider

class BookDescriptionFragment : Fragment(R.layout.fragment_book_description) {

    private val binding: FragmentBookDescriptionBinding by viewBinding()
    private val bindingError: ErrorStateBinding by viewBinding()
    private val bindingLoading: LoadingStateBinding by viewBinding()

    @Inject
    lateinit var viewModelFactory: Provider<BookDescriptionViewModel.Factory>
    private val viewModel: BookDescriptionViewModel by viewModels { viewModelFactory.get() }

    private val bookId get() = requireArguments().getInt("bookId")

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val bookCatalogSubcomponent =
            (requireActivity().application as BookDescriptionSubcomponentProvider).provideBookDescriptionSubcomponentProvider()

        bookCatalogSubcomponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setupWithNavController(findNavController())

        bindingError.buttonRefresh.setOnClickListener {
            viewModel.getPopularBookById(bookId)
        }

        viewModel.getPopularBookById(bookId)

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach(::render)
            .launchIn(lifecycleScope)
    }

    private fun render(lceState: LceState<BookModelUi>) {
        bindingError.textError.visibility = View.GONE
        bindingError.imageError.visibility = View.GONE
        bindingError.buttonRefresh.visibility = View.GONE
        bindingLoading.progressBar.visibility = View.GONE
        binding.scrollView.visibility = View.GONE

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
                binding.textAuthorName.text = lceState.data.author
                binding.textTitle.text = lceState.data.title
                binding.textInfo.text = lceState.data.description
                binding.textRating.text = lceState.data.rating.toString()

                binding.scrollView.visibility = View.VISIBLE

                Glide.with(binding.imPoster.context)
                    .load(lceState.data.posterUrl)
                    .placeholder(com.korett.core.ui.R.drawable.placeholder)
                    .into(binding.imPoster)
            }
        }
    }

}