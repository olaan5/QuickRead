package com.miniweam.quickread.model


import com.google.gson.annotations.SerializedName


data class QrAllResponse(
    @SerializedName("data")
    val data: List<Data>,
    @SerializedName("message")
    val message: String
)