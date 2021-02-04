package com.android.ranks.data.entities

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("src")
    val src: String?,
    @SerializedName("width")
    val width: Int?,
    @SerializedName("height")
    val height: Int
)