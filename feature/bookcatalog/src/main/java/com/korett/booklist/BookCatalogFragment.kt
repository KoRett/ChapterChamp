package com.korett.booklist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.korett.booklist.databinding.FragmentBookCatalogBinding

class BookCatalogFragment : Fragment() {

    private var _binding: FragmentBookCatalogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookCatalogBinding.inflate(inflater, container, false)

        binding.tbCatalog.inflateMenu(R.menu.menu_book_catalog)

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}