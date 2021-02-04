package com.android.ranks.data.entities

import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("date")
    val date: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("image")
    val image: Image?,
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail?,
    @SerializedName("link")
    val link: String?,
    @SerializedName("author")
    val author: Author?
)