package com.miniweam.quickread.model

import com.google.gson.annotations.SerializedName

data class NewsData(
    @SerializedName("date_published")
    val datePublished:String,
    val id:Int,
    @SerializedName("image_url")
    val imageUrl:String,
    val source:String,
    val title:String
)