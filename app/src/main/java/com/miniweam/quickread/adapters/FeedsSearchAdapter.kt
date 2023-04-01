package com.miniweam.quickread.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.miniweam.quickread.ItemsWithCategories
import com.miniweam.quickread.R
import com.miniweam.quickread.databinding.SearchResultViewholderBinding
import com.miniweam.quickread.model.Data
import com.miniweam.quickread.util.getDateFormatAsPeriod
@RequiresApi(Build.VERSION_CODES.O)
class FeedsSearchAdapter:  ListAdapter<Data, FeedsSearchAdapter.ViewHolder>(diffObject) {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = SearchResultViewholderBinding.bind(view)

        fun bind(item: Data) {
            binding.feedTitle.text = item.title
            binding.timeStamp.text = getDateFormatAsPeriod(item.datePublished)
            binding.feedImage.load(item.imgUrl) {
                crossfade(true)
                error(R.drawable.error_outline_24)
                placeholder(R.drawable.ic_launcher_foreground)
            }
            binding.root.setOnClickListener {
                listener?.let { it(item) }
            }
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
        val diffObject = object : DiffUtil.ItemCallback<Data>() {
            override fun areItemsTheSame(
                oldItem: Data,
                newItem: Data
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Data,
                newItem: Data
            ): Boolean {
                return oldItem.id == newItem.id && oldItem.title == newItem.title
            }
        }
    }

    private var listener: ((Data) -> Unit)? = null
    fun adapterClick(listener: (Data) -> Unit) {
        this.listener = listener
    }
}