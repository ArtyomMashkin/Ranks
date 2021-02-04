package com.android.ranks.data.entities

import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("name")
    val name: String?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("bio")
    val bio: String?,
    @SerializedName("image")
    val image: Image?
)