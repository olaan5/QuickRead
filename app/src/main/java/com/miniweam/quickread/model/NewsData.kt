package com.miniweam.quickread.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "saved_news_table")
data class NewsData(
    @PrimaryKey(autoGenerate = true)
    val tableId:Int =0,
    @SerializedName("date_published")
    val datePublished:String,
    val id:Int,
    @SerializedName("img_url")
    val imageUrl:String,
    val source:String,
    val title:String,
    val content:String
)