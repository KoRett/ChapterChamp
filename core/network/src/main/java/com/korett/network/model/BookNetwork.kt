package com.korett.network.model

import com.google.gson.annotations.SerializedName

data class BookNetwork(
    val id: Int,
    @SerializedName("poster_url") val posterUrl: String,
    val title: String,
    val description: String,
    val author: String,
    val rating: Float
)
