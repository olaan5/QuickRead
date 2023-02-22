package com.miniweam.quickread

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.miniweam.quickread.databinding.CategoryViewholderBinding
import com.miniweam.quickread.databinding.FeedsViewholderBinding

class FeedsCategoryAdapter: ListAdapter<String, FeedsCategoryAdapter.ViewHolder>(diffObject) {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = CategoryViewholderBinding.bind(view)
        fun bind(item: String) {
            binding.categoryText.text = item
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.category_viewholder, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object {
        val diffObject = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }

    private var listener: ((String) -> Unit)? = null
    fun adapterClick(listener: (String) -> Unit) {
        this.listener = listener
    }
}