package com.korett.bookcatalog.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.korett.bookcatalog.databinding.ItemQueryBinding

class SearchAdapter(
    private val itemClick: ((QueryModel) -> Unit)
) : ListAdapter<QueryModel, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            ItemQueryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemClick
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(getItem(position))
    }

    private class ViewHolder(
        private val binding: ItemQueryBinding,
        private val itemClick: ((QueryModel) -> Unit)
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: QueryModel) {
            binding.textSearch.text = model.query
            binding.root.setOnClickListener { itemClick(model) }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<QueryModel>() {
            override fun areItemsTheSame(oldItem: QueryModel, newItem: QueryModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: QueryModel, newItem: QueryModel): Boolean {
                return oldItem == newItem
            }
        }
    }

}