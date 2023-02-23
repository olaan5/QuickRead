package com.miniweam.quickread.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.miniweam.quickread.ItemsWithCategories
import com.miniweam.quickread.R
import com.miniweam.quickread.databinding.SearchResultViewholderBinding

class FeedsSearchAdapter:  ListAdapter<ItemsWithCategories, FeedsSearchAdapter.ViewHolder>(
    diffObject
) {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = SearchResultViewholderBinding.bind(view)
        fun bind(item: ItemsWithCategories) {
            binding.feedTitle.text = item.content
            binding.timeStamp.text = "${item.timeStamp}hrs ago"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.search_result_viewholder, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object {
        val diffObject = object : DiffUtil.ItemCallback<ItemsWithCategories>() {
            override fun areItemsTheSame(
                oldItem: ItemsWithCategories,
                newItem: ItemsWithCategories
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ItemsWithCategories,
                newItem: ItemsWithCategories
            ): Boolean {
                return oldItem.content == newItem.content && oldItem.hashCode() == newItem.hashCode()
            }
        }
    }

    private var listener: ((ItemsWithCategories) -> Unit)? = null
    fun adapterClick(listener: (ItemsWithCategories) -> Unit) {
        this.listener = listener
    }
}