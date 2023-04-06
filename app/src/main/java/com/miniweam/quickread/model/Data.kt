package com.miniweam.quickread.model


import com.google.gson.annotations.SerializedName


data class Data(
    @SerializedName("date_published")
    val datePublished: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("img_url")
    val imgUrl: String,
    @SerializedName("title")
    val title: String
)