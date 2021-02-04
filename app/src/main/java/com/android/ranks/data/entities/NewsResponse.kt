package com.android.ranks.data.entities

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("data")
    val data: List<News>
)