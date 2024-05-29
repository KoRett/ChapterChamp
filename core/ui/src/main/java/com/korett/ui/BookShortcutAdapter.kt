package com.korett.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.korett.core.ui.R
import com.korett.core.ui.databinding.ItemBookShortcutBinding
import com.korett.ui.model.BookModelUi

class BookShortcutAdapter(
    private val itemClick: ((BookModelUi) -> Unit),
    private val longItemClick: ((BookModelUi) -> Unit)? = null
) : ListAdapter<BookModelUi, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            ItemBookShortcutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemClick,
            longItemClick
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(getItem(position))
    }

    private class ViewHolder(
        private val binding: ItemBookShortcutBinding,
        private val itemClick: ((BookModelUi) -> Unit),
        private val longItemClick: ((BookModelUi) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: BookModelUi) {
            binding.bookTitle.text = model.title
            binding.bookAuthor.text = model.author

            binding.imageFavourite.isVisible = model.isFavourite

            Glide.with(binding.root.context)
                .load(model.posterUrl)
                .placeholder(R.drawable.placeholder)
                .into(binding.bookPreview)

            binding.root.setOnClickListener { itemClick(model) }
            if (longItemClick != null) {
                binding.root.setOnLongClickListener {
                    longItemClick.invoke(model)
                    true
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BookModelUi>() {
            override fun areItemsTheSame(
                oldItem: BookModelUi,
                newItem: BookModelUi
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: BookModelUi,
                newItem: BookModelUi
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}