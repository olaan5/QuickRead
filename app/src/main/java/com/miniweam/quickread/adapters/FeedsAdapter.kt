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
import com.miniweam.quickread.model.Data
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
@RequiresApi(Build.VERSION_CODES.O)
class FeedsAdapter : ListAdapter<Data, FeedsAdapter.ViewHolder>(diffObject) {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = FeedsViewholderBinding.bind(view)
        fun bind(item: Data) {
            binding.feedTitle.text = item.title
            binding.categoryText.text = "Category"
            binding.sourceText.text = "Source"
            binding.timeStamp.text = getDateFormat(item.datePublished)
            binding.feedImage.load(item.imgUrl){
                crossfade(true)
                error(R.drawable.error_outline_24)
                scale(Scale.FILL)
                placeholder(R.drawable.ic_launcher_foreground)
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
                return oldItem.id == newItem.id && oldItem.hashCode() == newItem.hashCode()
            }
        }
    }

    private var listener: ((Data) -> Unit)? = null
    fun adapterClick(listener: (Data) -> Unit) {
        this.listener = listener
    }



    private  fun getDateFormat(date: String):String{
        val format =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val localDate = LocalDateTime.parse(date, format)
        val dateFormatter = DateTimeFormatter.ofPattern("EEE, MMM d, yyyy 'at' hh:mm a", Locale.getDefault())
        val currentTime = LocalDateTime.now()

        val day = currentTime.dayOfYear-localDate.dayOfYear
        if (day > 7){
            return "Some time ago"
        }
        if (day == 7){
            return  "a week ago"
        }

        return( currentTime.hour-localDate.hour).toString()+"hrs ago" //localDate.format(dateFormatter)
    }
}