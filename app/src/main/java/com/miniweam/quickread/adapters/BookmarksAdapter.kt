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
import coil.size.Scale
import com.miniweam.quickread.ItemsWithCategories
import com.miniweam.quickread.R
import com.miniweam.quickread.databinding.FeedsViewholderBinding
import com.miniweam.quickread.model.NewsData
import com.miniweam.quickread.util.getDateFormatAsPeriod
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
@RequiresApi(Build.VERSION_CODES.O)
class BookmarksAdapter : ListAdapter<NewsData, BookmarksAdapter.ViewHolder>(diffObject) {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = FeedsViewholderBinding.bind(view)
        fun bind(item: NewsData) {
            binding.apply {
                feedTitle.text = item.title
                timeStamp.text = getDateFormatAsPeriod(item.datePublished)

                feedImage.load(item.imageUrl) {
                    crossfade(true)
                    error(R.drawable.error_outline_24)
                    placeholder(R.drawable.ic_launcher_foreground)
                }
                root.setOnClickListener {
                    listener?.let { it(item) }
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.feeds_viewholder, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object {
        val diffObject = object : DiffUtil.ItemCallback<NewsData>() {
            override fun areItemsTheSame(
                oldItem: NewsData,
                newItem: NewsData
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: NewsData,
                newItem: NewsData
            ): Boolean {
                return oldItem.id == newItem.id && oldItem.hashCode() == newItem.hashCode()
            }
        }
    }

    private var listener: ((NewsData) -> Unit)? = null
    fun adapterClick(listener: (NewsData) -> Unit) {
        this.listener = listener
    }
}